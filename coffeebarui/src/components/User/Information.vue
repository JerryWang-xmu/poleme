<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的账户</el-breadcrumb-item>
            <el-breadcrumb-item>账户信息</el-breadcrumb-item>
        </el-breadcrumb>


        <!-- 信息区 -->
        <el-card class="box-card">
            <!-- <div slot="header" class="clearfix">
               <span>酝酿享优，只因有你</span>
            </div> -->
            <div class="demo-type">
                <div style="padding-bottom: 30px">
                    <el-avatar :size=160 shape="square"
                               src="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1593500288149&di=045aa25152194bc1c74da33f460c97b3&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180929%2F16b01b1d6fa448819ee384c4ca00a0bd.jpeg"></el-avatar>
                </div>
            </div>
            <el-table :data="user" border>
                <el-table-column prop="id" label="ID号" width="120"></el-table-column>
                <el-table-column prop="username" label="用户名" width="180"></el-table-column>
                <el-table-column prop="phone" label="电话" width="180"></el-table-column>
                <el-table-column prop="email" label="邮箱" width="180"></el-table-column>
                <el-table-column prop="role" label="身份" width="180"></el-table-column>
                <el-table-column label="操作" width="80">
                    <template slot-scope="scope">
                        <el-button type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row.id)"></el-button>
                    </template>
                </el-table-column>
            </el-table>


        </el-card>

        <!-- 弹框 -->
        <el-dialog title="修改信息" :visible.sync="dialogVisible" width="30%">
            <span>请输入您的新信息</span>
            <span slot="footer" class="dialog-footer">

        <el-form ref="changeFormRef"
                 :model="changeForm"
                 :rules="changeRules"
        >
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="changeForm.username" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item label="电话号码" prop="phone">
                    <el-input v-model="changeForm.phone" prefix-icon="el-icon-phone-outline"
                              placeholder="电话号码"></el-input>
                </el-form-item>
                <el-form-item label="电子邮箱" prop="email">
                    <el-input v-model="changeForm.email" prefix-icon="el-icon-s-promotion" placeholder="邮箱"></el-input>
                </el-form-item>
            </el-form>
        <el-button @click="cancel()">取 消</el-button>
        <el-button type="primary" @click="makeSure()">确 定</el-button>
      </span>
        </el-dialog>
    </div>
</template>
<style lang="less" scoped>


    .box-card {
        width: 80%;
        transform: translate(5%, 10%);
    }

    ，
    .clearfix {
        color: black;
        position: absolute;
        transform: translate(-50%, -50%);
    }
</style>
<script>
    export default {
        created() {
            this.getUser();
        },
        data() {
            return {
                queryInfo: {
                    id: window.sessionStorage.getItem("id") // session 取出
                },
                changeRules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur'}
                    ],
                    phone: [
                        {required: true, message: '请输入电话号码', trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '请输入电子邮箱', trigger: 'blur'}
                    ]
                },
                dialogVisible: false,
                user: [],//虽然其实也只有一个
                changeForm: {
                    id: window.sessionStorage.getItem("id"), // session 取出
                    username: "",
                    phone: "",
                    email: "",
                },
                //还没写头像！！！
            };
        },
        methods: {
            //获取个人信息
            async getUser() {
                const {data: res} = await this.$http.post("info", this.queryInfo);
                if (res.str = "ok") {
                    this.user = res.user;
                    this.changeForm=this.user[0];
                }
            },
            showEditDialog(id) {
                //展示编辑的对话框
                this.dialogVisible = true;
            },
            cancel() {
                this.dialogVisible = false;
                //this.$refs.changeFormRef.resetFields();
            },
            makeSure() {
                this.dialogVisible = false;
                this.$refs.changeFormRef.validate(async valid => {
                    if (!valid) return;
                    // 调用get请求
                    const {data: res} = await this.$http.post("change", this.changeForm);
                    if (res == "ok") {
                        this.$message.success("修改成功！");
                        this.getUser();
                    } else this.$message.success("修改失败！");
                    this.$refs.changeFormRef.resetFields();
                });
            }
        }
    };
</script>