<template>
    <div class="main_welcome">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>菜单</el-breadcrumb-item>
            <el-breadcrumb-item>菜单管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图区 -->
        <el-card style="width: 85%">
            <el-row :gutter="25">
                <el-col :span="10">
                    <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getMenuList()">
                        <el-button slot="append" icon="el-icon-search" @click="getMenuList()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="8">
                    <el-button type="primary" @click="addDialogVisible = true">添加菜品</el-button>
                </el-col>
            </el-row>

            <!-- 菜单列表 -->
            <el-table class="menutable" :data="menulist" border stripe>
                <el-table-column type="index"></el-table-column>
                <!-- <el-table-column label="ID号" prop="id"></el-table-column> -->
                <el-table-column label="菜品名" prop="name" width="100"></el-table-column>
                <el-table-column label="类型" prop="style" width="90"></el-table-column>
                <el-table-column label="库存" prop="stock" width="60"></el-table-column>
                <el-table-column label="价格" prop="price" width="60"></el-table-column>
                <el-table-column label="收益" prop="proceeds" width="60"></el-table-column>
                <el-table-column label="菜品描述" prop="description"></el-table-column>
                <el-table-column label="图片链接" prop="imgurl"></el-table-column>
                <el-table-column label="属性标签" prop="path" width="80"></el-table-column>

                <el-table-column label="操作" width="120">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-tooltip effect="dark" content="修改信息" placement="top-start" :enterable="false">
                            <el-button style="padding-inline: 20px" type="primary" icon="el-icon-edit" size="mini"
                                       @click="showEditDialog(scope.row.id)"></el-button>
                        </el-tooltip>
                        <!-- 删除 -->
                        <el-tooltip effect="dark" content="删除" placement="top-start" :enterable="false">
                            <el-button style="padding-inline: 20px" type="danger" icon="el-icon-delete" size="mini"
                                       @click="deleteMenu(scope.row.id)"></el-button>
                        </el-tooltip>

                    </template>
                </el-table-column>
            </el-table>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pageNum"
                    :page-sizes="[1, 2, 5, 100]"
                    :page-size="queryInfo.pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
            ></el-pagination>
        </el-card>

        <!-- 创建新菜品对话框 -->
        <el-dialog title="添加菜品" :visible.sync="addDialogVisible" width="50%"
                   @close="addDialogClosed">
            <!-- 内容主体区域 -->
            <el-form :model="addForm" :rules="addFormRules"
                     ref="addFormRef" label-width="160px" >
                <!-- 菜品名 -->
                <el-form-item label="菜品名" prop="name">
                    <el-input v-model="addForm.name"></el-input>
                </el-form-item>
                <!-- 库存 -->
                <el-form-item label="库存" prop="stock">
                    <el-input v-model="addForm.stock"></el-input>
                </el-form-item>
                <!-- 类型 -->
                <el-form-item label="类型" prop="style">
                    <el-input v-model="addForm.style"></el-input>
                </el-form-item>
                <!-- 价格 -->
                <el-form-item label="价格" prop="price">
                    <el-input v-model="addForm.price"></el-input>
                </el-form-item>
                <!-- 图片链接 -->
                <el-form-item label="图片链接" prop="imgurl">
                    <el-input v-model="addForm.imgurl"></el-input>
                </el-form-item>
                <!-- 菜品描述 -->
                <el-form-item label="菜品描述" prop="description">
                    <el-input v-model="addForm.description"></el-input>
                </el-form-item>
                <!-- path -->
                <el-form-item label="属性标签(path)" prop="path">
                    <el-input v-model="addForm.path"></el-input>
                </el-form-item>
            </el-form>
            <!-- 内容底部区域 -->
            <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMenu()">确 定</el-button>
      </span>
        </el-dialog>
        <!-- 修改用户对话框 -->
        <el-dialog title="修改菜品信息" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="160px">
                <!-- 菜品名 -->
                <el-form-item label="菜品名" prop="name">
                    <el-input v-model="editForm.name"></el-input>
                </el-form-item>
                <!-- 库存 -->
                <el-form-item label="库存" prop="stock">
                    <el-input v-model="editForm.stock"></el-input>
                </el-form-item>
                <!-- 类型 -->
                <el-form-item label="类型" prop="style">
                    <el-input v-model="editForm.style"></el-input>
                </el-form-item>
                <!-- 价格 -->
                <el-form-item label="价格" prop="price">
                    <el-input v-model="editForm.price"></el-input>
                </el-form-item>
                <!-- 图片链接 -->
                <el-form-item label="图片链接" prop="imgurl">
                    <el-input v-model="editForm.imgurl"></el-input>
                </el-form-item>
                <!-- 图片链接 -->
                <el-form-item label="菜品描述" prop="description">
                    <el-input v-model="editForm.description"></el-input>
                </el-form-item>
                <!-- 图片链接 -->
                <el-form-item label="属性标签(path)" prop="path">
                    <el-input v-model="editForm.path"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editMenuInfo()">确 定</el-button>
      </span>
        </el-dialog>

    </div>
</template>
<style lang="less" scoped>

    .elspan {
        position: absolute;
        transform: translate(105%, 0%);
    }

    .el-radio {
        transform: translate(120%, 0%);
    }

    .el-card {
        transform: translate(3%, 8%);
    }

    .menutable {
        transform: translate(0, 10%);
        padding-bottom: 20px;
    }

    .el-pagination {
        padding-top: 130px;
    }
</style>
<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                radio: '1',
                queryInfo: {
                    query: "",
                    currentId: window.sessionStorage.getItem('id'),
                    pageNum: 1,
                    pageSize: 5,
                },
                menulist: [],// 菜单列表
                total: 100, // 最大数据记录
                addDialogVisible: false,// 对话框显示
                // 添加菜单单项
                addForm: {
                    name: "",
                    stock: "",
                    style: "",
                    imgurl: "",
                    price: "",
                    description:"",
                    path:"",
                },
                // 控制修改菜单对话框显示/隐藏
                editDialogVisible: false,
                // 修改菜单信息
                editForm: {
                    id: "",
                    name: "",
                    stock: "",
                    style: "",
                    imgurl: "",
                    price: "",
                    description:"",
                    path:"",
                },
                tempForm:{},
                // 验证规则
                addFormRules: {
                    name: [
                        {required: true, message: "请输入菜品名", trigger: "blur"},
                    ],
                    stock: [
                        {required: true, message: "请输入库存", trigger: "blur"},
                    ],
                    style: [
                        {required: true, message: "请输入菜品类型", trigger: "blur"},
                    ],
                    imgurl: [
                        {required: true, message: "请输入图片链接", trigger: "blur"},
                    ],
                    price: [
                        {required: true, message: "请输入价格", trigger: "blur"},
                    ],
                    description: [
                        {required: true, message: "请输入菜品描述", trigger: "blur"},
                    ],
                    path: [
                        {required: true, message: "请输入属性标签", trigger: "blur"},
                    ],
                },
                // 修改用户表单验证规则
                editFormRules: {
                    name: [
                        {required: true, message: "请输入菜品名", trigger: "blur"},
                    ],
                    stock: [
                        {required: true, message: "请输入库存", trigger: "blur"},
                    ],
                    style: [
                        {required: true, message: "请输入菜品类型", trigger: "blur"},
                    ],
                    imgurl: [
                        {required: true, message: "请输入图片链接", trigger: "blur"},
                    ],
                    price: [
                        {required: true, message: "请输入价格", trigger: "blur"},
                    ],
                    description: [
                        {required: true, message: "请输入菜品描述", trigger: "blur"},
                    ],
                    path: [
                        {required: true, message: "请输入属性标签", trigger: "blur"},
                    ],
                },
            }
        },
        created() {
            this.getMenuList();
        },
        methods: {
            async getMenuList() {
                const {data: res} = await this.$http.post("allMenu", this.queryInfo);
                if (res.menus != "-1") {
                    this.menulist = res.data;
                    this.total = res.total;
                    console.log(this.total);
                } else {
                    this.$message.error("对不起，您无权访问，请检查您的登录状态！");
                    this.$router.push({path: "/home"});
                    window.sessionStorage.setItem('activePath', "/mainpage")
                    this.reload();
                }
            },
            // 监听添加菜品
            addDialogClosed() {
                this.$refs.addFormRef.resetFields();// 重置表单项
            },
            handleSizeChange(newSize) {
                this.queryInfo.pageSize = newSize;
                this.getMenuList(); // 数据发生改变重新申请数据
            },
            // 监听pageNum改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pageNum = newPage;
                this.getMenuList(); // 数据发生改变重新申请数据
            },
            // 添加菜品
            addMenu() {
                this.$refs.addFormRef.validate(async valid => {
                    console.log(valid);
                    if (!valid) return;
                    // 发起请求
                    const {data: res} = await this.$http.post("addMenu", this.addForm);
                    if (res != "ok") {
                        // userinfo.state = !userinfo.state;//这里注意还没改！！！！！！！
                        return this.$message.error("操作失败！！！");
                    }
                    this.$message.success("操作成功！！！");
                    //隐藏
                    this.addDialogVisible = false;
                    this.getMenuList();
                })
            },
            // 展示修改框
            async showEditDialog(id) {
                console.log(id);
                this.tempForm.currentMenuId=id;
                console.log(this.tempForm);
                //const {data:res} = await this.$http.get("getMenuUpdate?id="+id);
                const {data: res} = await this.$http.post("id2menu", this.tempForm);
                if (res.flag = "ok") {
                    console.log("what???");
                    this.editForm=res.data;
                }
                this.editForm.id = id;
                this.editDialogVisible = true;
            },
            // 关闭窗口
            editDialogClosed() {
                this.$refs.editFormRef.resetFields();
            },
            // 确认修改
            editMenuInfo() {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid);
                    if (!valid) return;
                    // 发起请求
                    const {data: res} = await this.$http.put("editMenu", this.editForm);
                    console.log(res);
                    if (res != "ok") return this.$message.error("操作失败！");
                    this.$message.success("操作成功！！！");
                    //隐藏
                    this.editDialogVisible = false;
                    this.getMenuList();
                });
            },
            // 删除按钮
            async deleteMenu(id) {
                // 弹框
                const confirmResult = await this.$confirm('此操作将永久删除此菜品, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).catch(err => err)
                // 成功删除为confirm 取消为 cancel
                if (confirmResult != 'confirm') {
                    return this.$message.info("已取消删除");
                }
                const {data: res} = await this.$http.delete("deleteMenu?id=" + id);
                if (res != "ok") {
                    return this.$message.error("操作失败！！！");
                }
                this.$message.success("操作成功！！！");
                this.getMenuList();
            },

        }
    }
</script>