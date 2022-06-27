package com.jeemodel.unit.idcode.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeemodel.bean.rpc.Pong;
import com.jeemodel.bean.rpc.PongData;
import com.jeemodel.bean.rpc.PongTable;
import com.jeemodel.bean.rpc.PongUtils;
import com.jeemodel.core.web.controller.BaseController;
import com.jeemodel.data.utils.PageUtils;
import com.jeemodel.unit.idcode.bean.dto.UseSceneDTO;
import com.jeemodel.unit.idcode.bean.dto.UseSceneEditSave;
import com.jeemodel.unit.idcode.bean.dto.UseSceneListReq;
import com.jeemodel.unit.idcode.bean.entity.UseScene;
import com.jeemodel.unit.idcode.service.IUseSceneService;
import com.jeemodel.unit.manage.core.annotation.Log;
import com.jeemodel.unit.manage.core.enums.BusinessType;
import com.jeemodel.unit.manage.core.utils.poi.ExcelUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 统一标识编码使用场景后台管理API Controller
 * 
 * @author JeeModel
 * @date 2022-06-27
 */
@Api(tags = "【Jeemodel-统一标识编码】-使用场景")
@RestController
@RequestMapping("/idcode/useScene")
public class UseSceneController extends BaseController {

    @Autowired
    private IUseSceneService useSceneService;

    @ApiOperation(value = "查询-统一标识编码使用场景-列表")
    @PreAuthorize("@ss.hasPermi('idcode:useScene:list')")
    @GetMapping("/list")
    public PongTable<UseSceneDTO> list(UseSceneListReq listReq) {
        PageUtils.startPage();
        List<UseScene> list = useSceneService.selectUseSceneList(listReq);
		
        List<UseSceneDTO> listDTO = list.stream().map(UseScene::toDTO).collect(Collectors.toList());
        return PageUtils.okTable(listDTO);
    }


    @ApiOperation(value = "导出-统一标识编码使用场景-列表")
    @PreAuthorize("@ss.hasPermi('idcode:useScene:export')")
    @Log(title = "统一标识编码使用场景", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UseSceneListReq listReq) {
        List<UseScene> list = useSceneService.selectUseSceneList(listReq);
        List<UseSceneDTO> listDTO = list.stream().map(UseScene::toDTO).collect(Collectors.toList());
        ExcelUtil<UseSceneDTO> util = new ExcelUtil<>(UseSceneDTO.class);
        util.exportExcel(response, listDTO, "统一标识编码使用场景数据");
    }
	

    @ApiOperation(value = "查询-统一标识编码使用场景-详情")
    @PreAuthorize("@ss.hasPermi('idcode:useScene:query')")
    @GetMapping(value = "/{id}")
    public PongData<UseSceneDTO> getInfo(@PathVariable("id") Long id) {
        UseScene useScene = useSceneService.getById(id);
        UseSceneDTO useSceneDTO = useScene==null ? null:useScene.toDTO();
        return PongUtils.okData(useSceneDTO);
    }

    @ApiOperation(value = "新增-统一标识编码使用场景")
    @PreAuthorize("@ss.hasPermi('idcode:useScene:add')")
    @Log(title = "统一标识编码使用场景", businessType = BusinessType.INSERT)
    @PostMapping
    public Pong add(@RequestBody UseSceneEditSave useSceneEditSave) {
        return PongUtils.result(useSceneService.insertUseScene(useSceneEditSave));
    }

    @ApiOperation(value = "修改-统一标识编码使用场景")
    @PreAuthorize("@ss.hasPermi('idcode:useScene:edit')")
    @Log(title = "统一标识编码使用场景", businessType = BusinessType.UPDATE)
    @PutMapping
    public Pong edit(@RequestBody UseSceneEditSave useSceneEditSave) {
        return PongUtils.result(useSceneService.updateUseScene(useSceneEditSave));
    }	

    @ApiOperation(value = "删除-统一标识编码使用场景")
    @PreAuthorize("@ss.hasPermi('idcode:useScene:remove')")
    @Log(title = "统一标识编码使用场景", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public Pong remove(@PathVariable Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return PongUtils.fail("请选择需要删除的数据");
        }
        List<Long> idList = Arrays.asList(ids);
        return PongUtils.result(useSceneService.removeByIds(idList));
    }
}
