package com.jeemodel.unit.idcode.server.helper;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeemodel.base.annotation.HelpService;
import com.jeemodel.bean.exception.type.ServiceException;
import com.jeemodel.core.utils.StringUtils;
import com.jeemodel.core.utils.id.SnowFlakeHelper;
import com.jeemodel.core.utils.spring.SpringUtils;
import com.jeemodel.unit.idcode.bean.entity.UseScene;
import com.jeemodel.unit.idcode.common.bean.IDCodeDemandDTO;
import com.jeemodel.unit.idcode.common.bean.ProtoDTO;
import com.jeemodel.unit.idcode.config.IDCodeServerConfig;
import com.jeemodel.unit.idcode.mapper.UseSceneMapper;

@HelpService
public class IDcodeCodeHelper {

	@Resource(name = IDCodeServerConfig.IDCODE_SNOWFLAKE)
	private SnowFlakeHelper uniqueidSnowFlake;

	@Resource(name = IDCodeServerConfig.ECHO_SNOWFLAKE)
	private SnowFlakeHelper echoSnowFlake;

	@Resource
	private UseSceneMapper useSceneMapper;

	/**
	 * 下一个全局ID
	 * @return
	 */
	public long nextUID() {
		return uniqueidSnowFlake.nextId();
	}

	/**
	 * 下一个回声
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
		Long lastUidSerial = lastUseScene.getUidSerial();

		String[] needCodes = new String[needSize];
		for (int i = 1; i <= needSize; i++) {
			Long uidSerial = lastUidSerial - (needSize - i);
			needCodes[i - 1] = nextSceneUID(sceneCode, sceneName, uidLength, uidSerial);
		}
		return needCodes;
	}

	/**
	 * 下一个场场景码,
	 * 生成的场景码最多由三部分组成
	 * 1、场景码
	 * 2、补位0（可能会有）
	 * 3、UID序列号（转成36进制的大写字符串形式）
	 * @return
	 */
	public String nextSceneUID(String sceneCode, String sceneName, Integer uidLength, Long uidSerial) {
		// UID上限制（不包含）
		long uidUpLimit = 0L;
		switch (uidLength) {
		case 3:
			// UID长度3，上限:46656 = 36 的3次方 */
			uidUpLimit = 46656L;
			break;
		case 4:
			// UID长度4，上限:1679616 = 36 的4次方 */
			uidUpLimit = 1679616L;
			break;
		case 5:
			// UID长度5，上限:60466176 = 36 的5次方 */
			uidUpLimit = 60466176L;
			break;
		default:
			// UID长度6，上限:2176782336 = 36 的6次方 */
			uidUpLimit = 2176782336L;
			break;
		}

		if (uidSerial >= uidUpLimit) {
			throw ServiceException.subServiceDown("{}-统一标识编码使用已经达到上限：{}", sceneName, uidUpLimit);
		}

		String upperCaseUID = Long.toString(uidSerial, 36).toUpperCase();
		Integer alternate = uidLength - upperCaseUID.length();
		StringBuilder sb = new StringBuilder(sceneCode);
		if (alternate > 0) {
			for (int i = 0; i < alternate; i++) {
				sb.append(0);
			}
		}
		sb.append(upperCaseUID);

		return sb.toString();
	}
	
	
	/**
	 * 返回增量后的 UidSerial
	 * @param sceneCode 统一标识编码
	 * @param incrQuantity  增量
	 * @return
	 */
	@Transactional
	public Long incrUidSerial(String sceneCode,int incrQuantity) {
		useSceneMapper.autoIncrUidSerial(sceneCode,incrQuantity);
		return useSceneMapper.queryUidSerial(sceneCode);
	}

	/**
	 * 返回增量后的 UseScene
	 * @param sceneCode 统一标识编码
	 * @param incrQuantity  增量
	 * @return
	 */
	@Transactional
	public UseScene incrUidSerialReturnUseScene(String sceneCode,int incrQuantity) {
		useSceneMapper.autoIncrUidSerial(sceneCode,incrQuantity);
		return useSceneMapper.selectOne(new QueryWrapper<UseScene>().eq("scene_code", sceneCode));
	}
	
}
