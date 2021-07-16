<template>
    <div class="main_welcome">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 卡片视图区 -->
        <el-card style="width: 85%">
            <el-row :gutter="25">
                <el-col :span="10">
                    <el-input placeholder="请输入搜索内容" v-model="queryInfo.query" clearable @clear="getUserList()">
                        <el-button slot="append" icon="el-icon-search" @click="getUserList()"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="8">
                    <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
                </el-col>
            </el-row>

            <!-- 用户列表 -->
            <el-table class="usertable" :data="userlist" border stripe>
                <el-table-column type="index"></el-table-column>
                <el-table-column label="姓名" prop="username"></el-table-column>
                <el-table-column label="邮箱" prop="email"></el-table-column>
                <el-table-column label="电话" prop="phone"></el-table-column>
                <el-table-column label="密码" prop="password"></el-table-column>
                <el-table-column label="角色" prop="role"></el-table-column>
                <!--                <el-table-column label="状态" prop="state">-->
                <!--                <template slot-scope="scope">-->
                <!--                    <el-switch v-model="scope.row.state" ></el-switch>-->
                <!--                </template>-->
                <!--                </el-table-column>-->
                <el-table-column label="操作" width="250%">
                    <template slot-scope="scope">
                        <!-- 修改 -->
                        <el-tooltip effect="dark" content="修改信息" placement="top-start" :enterable="false">
                            <el-button
                                    style="padding-inline: 20px"
                                    type="primary"
                                    icon="el-icon-edit"
                                    size="mini"
                                    @click="showEditDialog(scope.row.id)"
                            ></el-button>
                        </el-tooltip>
                        <!-- 删除 -->
                        <el-tooltip effect="dark" content="删除" placement="top-start" :enterable="false">
                            <el-button
                                    style="padding-inline: 20px"
                                    type="danger"
                                    icon="el-icon-delete"
                                    size="mini"
                                    @click="deleteUser(scope.row.id)"
                            ></el-button>
                        </el-tooltip>
                        <!-- 权限 -->
                        <el-tooltip effect="dark" content="分配权限" placement="top-start" :enterable="false">
                            <el-button
                                    style="padding-inline: 20px"
                                    type="warning"
                                    icon="el-icon-setting"
                                    size="mini"
                                    @click="changeRight(scope.row.id)"
                            ></el-button>
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

        <!-- 创建新用户对话框 -->
        <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="40%" @close="addDialogClosed">
            <!-- 内容主体区域 -->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
                <!-- 用户名 -->
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="addForm.username"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item label="密码" prop="password">
                    <el-input v-model="addForm.password"></el-input>
                </el-form-item>
                <!-- 电话 -->
                <el-form-item label="电话" prop="username">
                    <el-input v-model="addForm.phone"></el-input>
                </el-form-item>
                <!-- 邮箱 -->
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="addForm.email"></el-input>
                </el-form-item>
                <!-- 角色 -->
                <el-form-item label="身份" prop="role">
                    <el-radio-group v-model="addForm.role">
                        <el-radio label="管理员"></el-radio>
                        <el-radio label="用户"></el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-form>
            <!-- 内容底部区域 -->
            <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
        </el-dialog>
        <!-- 修改用户对话框 -->
        <el-dialog
                title="修改用户信息"
                :visible.sync="editDialogVisible"
                width="40%"
                @close="editDialogClosed"
        >
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
                <!-- 用户名 -->
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="editForm.username"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item label="密码" prop="password">
                    <el-input v-model="editForm.password" disabled></el-input>
                </el-form-item>
                <el-form-item label="电话" prop="phone">
                    <el-input v-model="editForm.phone"></el-input>
                </el-form-item>
                <!-- 邮箱 -->
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
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

    .usertable {
        transform: translate(0, 10%);
        padding-bottom: 20px;
    }

    .el-pagination {
        /*transform: translate(10%,40%);*/
        padding-top: 130px;
    }
</style>
<script>
    export default {
        inject: ["reload"],
        data() {
            return {
                radio: "1",
                queryInfo: {
                    query: "",
                    currentId: window.sessionStorage.getItem("id"),
                    pageNum: 1,
                    pageSize: 5
                },
                userlist: [], // 用户列表
                total: 100, // 最大数据记录
                addDialogVisible: false, // 对话框显示
                // 添加用户表单项
                addForm: {
                    username: "",
                    password: "",
                    phone: "",
                    email: "",
                    role: ""
                },
                // 控制修改用户对话框显示/隐藏
                editDialogVisible: false,
                // 修改用户信息
                editForm: {
                    id: "",
                    username: "",
                    // password: "",
                    phone:"",
                    email: ""
                },
                tempForm:{},
                // 验证规则
                addFormRules: {
                    username: [
                        {required: true, message: "请输入用户名", trigger: "blur"},
                        {min: 3, max: 10, message: "长度在 3 到 10 个字符", trigger: "blur"}
                    ],
                    password: [
                        {required: true, message: '密码为必填项', trigger: 'blur'},
                        {required: true, message: '请输入密码', transform: value => value, trigger: 'blur'},
                        {
                            type: 'string',
                            message: '请输入不包含空格的字符',
                            trigger: 'blur',
                            transform(value) {
                                if (value && value.indexOf(' ') === -1) {
                                    return value
                                } else {
                                    return false
                                }
                            }
                        },
                        {
                            trigger: 'blur',
                            validator: (rule, value, callback) => {
                                var passwordreg = /(?=.*\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{6,16}/
                                if (!passwordreg.test(value)) {
                                    callback(new Error('密码必须由数字、字母、特殊字符组合,请输入6-16位'))
                                } else {
                                    callback()
                                }
                            }
                        }
                    ],
                    email: [
                        {required: true, message: "请输入邮箱", trigger: "blur"},
                        {min: 5, max: 20, message: "请输入正确邮箱地址", trigger: "blur"}
                    ],
                    phone: [
                        {required: true, message: "请输入联系电话", trigger: "blur"},
                        {min: 5, max: 15, message: "请输入正确联系电话", trigger: "blur"}
                    ],
                    role: [{required: true, message: "请输入角色", trigger: "change"}]
                },
                // 修改用户表单验证规则
                editFormRules: {
                    username: [
                        {required: true, message: "请输入用户名", trigger: "blur"},
                        {min: 1, max: 8, message: "长度在 1 到 8 个字符", trigger: "blur"}
                    ],
                    // password: [
                    //     {required: true, message: "请输入密码", trigger: "blur"},
                    //     {min: 1, max: 50, message: "长度在 6 到 8 个字符", trigger: "blur"}
                    // ],
                    phone: [
                        {required: true, message: "请输入联系电话", trigger: "blur"},
                        {min: 5, max: 15, message: "请输入正确联系电话", trigger: "blur"}
                    ],
                    email: [
                        {required: true, message: "请输入邮箱", trigger: "blur"},
                        {min: 5, max: 20, message: "请输入正确邮箱地址", trigger: "blur"}
                    ]
                }
            };
        },
        created() {
            this.getUserList();
        },
        methods: {
            async getUserList() {
                const {data: res} = await this.$http.post("alluser", this.queryInfo);
                if (res.users != "-1") {
                    this.userlist = res.data;
                    this.total = res.users;
                    console.log(this.total);
                } else {
                    this.$message.error("对不起，您无权访问，请检查您的登录状态！");
                    this.$router.push({path: "/home"});
                    window.sessionStorage.setItem("activePath", "/mainpage");
                    this.reload();
                }
            },
            // 监听添加用户
            addDialogClosed() {
                this.$refs.addFormRef.resetFields(); // 重置表单项
            },
            handleSizeChange(newSize) {
                this.queryInfo.pageSize = newSize;
                this.getUserList(); // 数据发生改变重新申请数据
            },
            // 监听pageNum改变的事件
            handleCurrentChange(newPage) {
                this.queryInfo.pageNum = newPage;
                this.getUserList(); // 数据发生改变重新申请数据
            },
            // 添加用户
            addUser() {
                this.$refs.addFormRef.validate(async valid => {
                    console.log(valid);
                    if (!valid) return;
                    // 发起请求
                    const {data: res} = await this.$http.post("addUser", this.addForm);
                    if (res != "success") {
                        userinfo.state = !userinfo.state;
                        return this.$message.error("操作失败！！！");
                    }
                    this.$message.success("操作成功！！！");
                    //隐藏
                    this.addDialogVisible = false;
                    this.getUserList();
                });
            },
            // 展示修改框
            async showEditDialog(id) {
                this.tempForm.id=id;
                const {data: res} = await this.$http.post("info", this.tempForm);
                if (res.str = "ok") {
                    this.user = res.user;
                    this.editForm=this.user[0];
                }
                // this.editForm.id = id;
                // this.editForm.password
                this.editDialogVisible = true;
            },
            // 关闭窗口
            editDialogClosed() {
                this.$refs.editFormRef.resetFields();
            },
            // 确认修改
            editUserInfo() {
                this.$refs.editFormRef.validate(async valid => {
                    console.log(valid);
                    if (!valid) return;
                    // 发起请求
                    const {data: res} = await this.$http.put("editUser", this.editForm);
                    console.log(res);
                    if (res != "success") return this.$message.error("操作失败！");
                    this.$message.success("操作成功！！！");
                    //隐藏
                    this.editDialogVisible = false;
                    this.getUserList();
                });
            },
            // 删除按钮
            async deleteUser(id) {
                // 弹框
                if (id == window.sessionStorage.getItem("id")) {
                    return this.$message.error("不能删除当前正在操作的用户！！！");
                }
                const confirmResult = await this.$confirm(
                    "此操作将永久删除该用户, 是否继续?",
                    "提示",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    }
                ).catch(err => err);
                // 成功删除为confirm 取消为 cancel
                if (confirmResult != "confirm") {
                    return this.$message.info("已取消删除");
                }
                const {data: res} = await this.$http.delete("deleteUser?id=" + id);
                if (res != "success") {
                    return this.$message.error("操作失败！！！");
                }
                this.$message.success("操作成功！！！");
                this.getUserList();
            },
            async changeRight(id) {
                const confirmResult = await this.$confirm(
                    "此操作取消/设置该用户的管理员权限, 是否继续?",
                    "提示",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    }
                ).catch(err => err);
                // 成功删除为confirm 取消为 cancel
                if (confirmResult != "confirm") {
                    return this.$message.info("已取消权限分配");
                }
                const {data: res} = await this.$http.get("revokeUser?id=" + id);
                if (res != "success") {
                    return this.$message.error("操作失败！！！");
                }
                this.$message.success("操作成功！！！");
                this.getUserList();
            }
        }
    };
</script>