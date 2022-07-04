package com.jeemodel.unit.idcode.server.helper;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.id.SnowFlakeHelper;
import com.jeemodel.core.utils.spring.SpringUtils;
import com.jeemodel.unit.idcode.bean.entity.UseScene;
import com.jeemodel.unit.idcode.common.bean.IDCodeDemandDTO;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;
import com.jeemodel.unit.idcode.config.IDCodeServerConfig;
import com.jeemodel.unit.idcode.mapper.UseSceneMapper;
import com.jeemodel.unit.idcode.utils.IDcodeUtils;

@HelpService
@ConditionalOnProperty(prefix = "jeemodel.unit.idcode", name = "deploy", havingValue = "server")
public class IDcodeCodeHelper {

	@Resource(name = IDCodeServerConfig.IDCODE_SNOWFLAKE)
	private SnowFlakeHelper uniqueidSnowFlake;

	@Resource(name = IDCodeServerConfig.ECHO_SNOWFLAKE)
	private SnowFlakeHelper echoSnowFlake;

	@Resource
	private UseSceneMapper useSceneMapper;

	/**
	 * 下一个全局ID
	 * 
	 * @return
	 */
	public long nextUID() {
		return uniqueidSnowFlake.nextId();
	}

	/**
	 * 下一个回声
	 * 
	 * @return
	 */
	public String nextEcho() {
		return new StringBuilder("ECHO").append(echoSnowFlake.nextId()).toString();
	}

	/**
	 * @param scene 场景
	 * @return
	 */
	public ProtoDTO nextUIDCode(IDCodeDemandDTO demand) {
		String scene = demand.getScene();
		int needSize = demand.getNeedSize();

		if (StringUtils.equalsIgnoreCase(scene, IDCodeDemandDTO.SCENE_UID)) {
			//全局唯一ID
			long[] needIds = new long[needSize];
			for (int i = 0; i < needSize; i++) {
				needIds[i] = nextUID();
			}

			return new ProtoDTO(scene, needIds);
		} else if (StringUtils.equalsIgnoreCase(scene, IDCodeDemandDTO.SCENE_ECHO)) {
			//回声
			String[] needCodes = new String[needSize];
			for (int i = 0; i < needSize; i++) {
				needCodes[i] = nextEcho();
			}
			return new ProtoDTO(scene, needCodes);
		} else {
			//统一标识编码
			UseScene lastUseScene = SpringUtils.getAopProxy(this).incrUidSerialReturnUseScene(scene, needSize);
			String[] needCodes = builderNeedSceneUID(lastUseScene, needSize);
			return new ProtoDTO(scene, needCodes);
		}
	}

	private String[] builderNeedSceneUID(UseScene lastUseScene, int needSize) {
		String sceneCode = lastUseScene.getSceneCode();
		String sceneName = lastUseScene.getSceneName();
		Integer uidLength = lastUseScene.getUidLength();
		Integer lastUidSerial = lastUseScene.getUidSerial();

		String[] needCodes = new String[needSize];
		for (int i = 1; i <= needSize; i++) {
			Integer uidSerial = lastUidSerial - (needSize - i);
			needCodes[i - 1] = IDcodeUtils.nextSceneIDCode(sceneCode, sceneName, uidLength, uidSerial);
		}
		return needCodes;
	}


	/**
	 * 返回增量后的 UidSerial
	 * 
	 * @param sceneCode    统一标识编码
	 * @param incrQuantity 增量
	 * @return
	 */
	@Transactional
	public Long incrUidSerial(String sceneCode, int incrQuantity) {
		useSceneMapper.autoIncrUidSerial(sceneCode, incrQuantity);
		return useSceneMapper.queryUidSerial(sceneCode);
	}

	/**
	 * 返回增量后的 UseScene
	 * 
	 * @param sceneCode    统一标识编码
	 * @param incrQuantity 增量
	 * @return
	 */
	@Transactional
	public UseScene incrUidSerialReturnUseScene(String sceneCode, int incrQuantity) {
		useSceneMapper.autoIncrUidSerial(sceneCode, incrQuantity);
		return useSceneMapper.selectOne(new QueryWrapper<UseScene>().eq("scene_code", sceneCode));
	}

}
