<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的账户</el-breadcrumb-item>
            <el-breadcrumb-item>密码设置</el-breadcrumb-item>
        </el-breadcrumb>

        <el-card class="box-card">

            <!--添加表单-->
            <el-form
                    ref="secretFormRef"
                    :model="secretForm"
                    :rules="secretRules"
                    class="secret_form"
                    label-width="0px"

            >
                <el-form-item prop="password">
                    <el-input v-model="secretForm.password" prefix-icon="el-icon-unlock" type="password"
                              placeholder="原密码"></el-input>
                </el-form-item>
                <el-form-item prop="newpassword">
                    <el-input v-model="secretForm.newpassword" prefix-icon="el-icon-lock" type="password"
                              placeholder="新密码"></el-input>
                </el-form-item>
                <el-form-item prop="newpasswordcheck">
                    <el-input v-model="secretForm.newpasswordcheck" prefix-icon="el-icon-lock" type="password"
                              placeholder="确认重复密码">
                    </el-input>
                </el-form-item>
                <el-form-item class="btns">
                    <el-button type="primary" @click="secret">提交</el-button>
                    <el-button type="info" @click="resetsecretForm">重置</el-button>
                </el-form-item>
            </el-form>

        </el-card>


    </div>
</template>


<script>
    export default {
        data() {
            return {

                count: "",
                secretForm: {
                    id: window.sessionStorage.getItem("id"), // session 取出
                    password: "",
                    newpassword: "",
                    newpasswordcheck: "",
                },
                secretRules: {
                    password: [
                        {required: true, message: '原密码为必填项', trigger: 'blur'},
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
                    newpassword: [
                        {required: true, message: '新密码为必填项', trigger: 'blur'},
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
                    newpasswordcheck: [
                        {required: true, message: '新密码为必填项', trigger: 'blur'},
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
                                } else if (value != this.secretForm.newpassword) {
                                    callback(new Error('两次输入的密码不一致'))
                                } else {
                                    callback()
                                }
                            }
                        }
                    ],
                }
            };
        },
        methods: {
            resetsecretForm() {
                this.$refs.secretFormRef.resetFields();
            },
            secret() {
                this.$refs.secretFormRef.validate(async valid => {
                    if (!valid) return;
                    // 调用get请求
                    const {data: res} = await this.$http.post("secret", this.secretForm);
                    if (res == "ok") {
                        this.$message.success("密码修改成功！您的登录状态已过期，系统将在3秒钟内自动返回登录界面，请重新登录！");
                        this.goGrdoupRecor();
                    } else {
                        this.$message.error("密码修改失败！");
                    }
                });
            },
            goGrdoupRecor() {
                const TIME_COUNT = 3;
                if (!this.timer) {
                    this.count = TIME_COUNT;
                    this.show = false;
                    this.timer = setInterval(() => {
                        if (this.count > 0 && this.count <= TIME_COUNT) {
                            this.count--;
                        } else {
                            this.show = true;
                            clearInterval(this.timer);
                            this.timer = null;
                            //跳转的页面写在此处
                            this.$router.push({path: '/login'});
                        }
                    }, 1000)
                }
            },
        }
    };
</script>

<style lang="less" scoped>
    .box-card {
        width: 400px;
        transform: translate(70%, 20%);
    }

</style>