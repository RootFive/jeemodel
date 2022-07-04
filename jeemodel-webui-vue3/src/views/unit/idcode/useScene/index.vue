<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="场景名称" prop="sceneName">
        <el-input
          v-model="queryParams.sceneName"
          placeholder="请输入标识的场景名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="场景标识" prop="sceneCode">
        <el-input
          v-model="queryParams.sceneCode"
          placeholder="请输入场景标识"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标识长度" prop="uidLength">
        <el-input
          v-model="queryParams.uidLength"
          placeholder="请输入标识的长度"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="标识序列" prop="uidSerial">
        <el-input
          v-model="queryParams.uidSerial"
          placeholder="请输入标识序列号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建者" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建者"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option
            v-for="dict in jeemodel_common_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更新者" prop="updateBy">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入更新者"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="更新时间" style="width: 308px">
        <el-date-picker
          v-model="daterangeUpdateTime"
          value-format="YYYY-MM-DD"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['idcode:useScene:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['idcode:useScene:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['idcode:useScene:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['idcode:useScene:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="useSceneList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="场景标识-名称" align="center" prop="sceneName" />
      <el-table-column label="场景标识-编码" align="center" prop="sceneCode" />
      <el-table-column label="标识-长度" align="center" prop="uidLength" />
      <el-table-column label="标识序列" align="center" prop="uidSerial" />
      <el-table-column label="备注" align="center" prop="remark" />      
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="jeemodel_common_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" prop="createBy" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['idcode:useScene:edit']"
          >修改</el-button>
          <el-button
            type="text"
            icon="Delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['idcode:useScene:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改场景标识对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="useSceneRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="场景名称" prop="sceneName">
          <el-input v-model="form.sceneName" placeholder="请输入场景标识的名称" />
        </el-form-item>
        <el-form-item v-if="form.id == undefined" label="标识长度" prop="uidLength">
          <el-input v-model="form.uidLength" placeholder="请输入标识的长度" />
        </el-form-item>
        <el-form-item v-if="form.id == undefined" label="序列起始" prop="uidSerial">
          <el-input v-model="form.uidSerial" placeholder="请输入标识序列号的起始号码" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in jeemodel_common_normal_disable"
              :key="dict.value"
:label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="UseScene">
import { listUseScene, getUseScene, delUseScene, addUseScene, updateUseScene } from "@/api/unit/idcode/useScene";

const { proxy } = getCurrentInstance();
const { jeemodel_common_normal_disable } = proxy.useDict('jeemodel_common_normal_disable');

const useSceneList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const daterangeUpdateTime = ref([]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    sceneName: null,
    sceneCode: null,
    uidLength: null,
    uidSerial: null,
    createBy: null,
    status: null,
    updateBy: null,
    updateTime: null,
  },
  rules: {
    sceneName: [
      { required: true, message: "场景标识-名称不能为空", trigger: "blur" }
    ],
    uidLength: [
      { required: true, message: "标识-长度不能为空", trigger: "blur" }
    ],
  }
});

const { queryParams, form, rules } = toRefs(data);

/** 查询场景标识列表 */
function getList() {
  loading.value = true;
  queryParams.value.params = {};
  if (null != daterangeUpdateTime && '' != daterangeUpdateTime) {
    queryParams.value.params["beginUpdateTime"] = daterangeUpdateTime.value[0];
    queryParams.value.params["endUpdateTime"] = daterangeUpdateTime.value[1];
  }
  listUseScene(queryParams.value).then(response => {
    useSceneList.value = response.data;
    total.value = response.total;
    loading.value = false;
  });
}

// 取消按钮
function cancel() {
  open.value = false;
  reset();
}

// 表单重置
function reset() {
  form.value = {
    id: null,
    sceneName: null,
    sceneCode: null,
    uidLength: null,
    uidSerial: null,
    remark: null,
    createBy: null,
    createTime: null,
    status: "0",
    updateBy: null,
    updateTime: null,
    delFlag: null,
    delTime: null
  };
  proxy.resetForm("useSceneRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  daterangeUpdateTime.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加场景标识";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const id = row.id || ids.value
  getUseScene(id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改场景标识";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["useSceneRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateUseScene(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addUseScene(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const useSceneIds = row.id || ids.value;
  proxy.$modal.confirm('是否确认删除场景标识编号为"' + useSceneIds + '"的数据项？').then(function() {
    return delUseScene(useSceneIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {});
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('idcode/useScene/export', {
    ...queryParams.value
  }, `useScene_${new Date().getTime()}.xlsx`)
}

getList();
</script>
