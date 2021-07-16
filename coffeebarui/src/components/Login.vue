<template>
    <div class="login_container">
        <!--        引入星空背景-->
        <vue-particles>
            .login_container{
            color="#dedede"
            :particleOpacity="0.7"
            :particlesNumber="80"
            shapeType="star"
            :particleSize="4"
            linesColor="#FFFFFF"
            :linesWidth="2"
            :lineLinked="true"
            :lineOpacity="0.4"
            :linesDistance="150"
            :moveSpeed="3"
            :hoverEffect="true"
            hoverMode="grab"
            :clickEffect="true"
            clickMode="push"
            }
        </vue-particles>

        <div class="register_box">
            <div class="avatar_box">
                <!--头像-->
                <img src="../assets/cafe.jpg" alt/>
            </div>
            <!--添加表单-->
            <el-form
                    ref="loginFormRef"
                    :model="loginForm"
                    :rules="loginRules"
                    class="register_form"
                    label-width="0px"
            >
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" prefix-icon="el-icon-user" placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input v-model="loginForm.password" prefix-icon="el-icon-unlock" type="password"
                              placeholder="密码"></el-input>
                </el-form-item>
                <!--                添加switch组件-->
                <el-switch prop="role"
                           v-model="loginForm.role"
                           active-text="以管理员身份登录"
                           inactive-text=""
                           active-value="管理员"
                           inactive-value="用户">
                </el-switch>
                <el-link class="el-l" type="primary" :underline="false"  @click="registerUser">点击注册</el-link>

                <!--                添加登录和重置按钮-->
                <el-form-item class="btns">
                    <el-button type="primary" @click="login">登录</el-button>
                    <el-button type="info" @click="resetLoginForm">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    export default {

        data() {
            return {
                loginForm: {
                    username: window.sessionStorage.getItem('usname'),
                    password: "",
                    role: "",
                },
                //登录规则约束
                loginRules: {
                    username: [
                        {required: true, message: '用户名称为必填项', trigger: 'blur'},
                        {min: 3, max: 10, message: '长度在3~10个字符之间', trigger: 'blur'},
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

                }
            };
        },
        methods: {
            resetLoginForm() {
                this.$refs.loginFormRef.resetFields();
            },
            login() {
                this.$refs.loginFormRef.validate(async valid => {
                    if (!valid) return;
                    // 调用get请求
                    window.sessionStorage.setItem('usname', this.loginForm.username);
                    const {data: res} = await this.$http.post("login", this.loginForm);
                    if (res.str == "USOK") {
                        window.sessionStorage.setItem('id', res.count); // session 放置
                        this.$message.success("用户登陆成功！！！");
                        this.$router.push({path: "/home"});
                    }
                    else if(res.str=="MNOK"){
                        window.sessionStorage.setItem('id', res.count); // session 放置
                        this.$message.success("管理员登陆成功！！！");
                        this.$router.push({path: "/home"});
                    }
                    else {
                        this.$message.error("登录失败！！！请检查用户名和密码！！！");
                    }
                });
            },
            registerUser(){
                this.$router.push({path: "/register"});
            },
        }
    };
</script>

<style lang="less" scoped>
    .login_container {
        background-color: rgb(110, 98, 85);
        height: 100%;
    }
    .el-l{
        display: flex;
        justify-content: flex-start;
        transform: translate(0%,35px);
    }
    .register_box {
        width: 450px;
        height: 315px;
        background-color: #fff;
        border-radius: 5px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);

        .avatar_box {
            width: 130px;
            height: 130px;
            border: 1px solid #eee;
            border-radius: 50%;
            padding: 10px;
            box-shadow: 0 0 10px #ddd;
            position: absolute;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #ffffff;

            img {
                width: 100%;
                height: 100%;
                border-radius: 50%;
            }
        }
    }

    .btns {
        display: flex;
        justify-content: flex-end;

    }

    .register_form {
        position: absolute;
        bottom: 0%;
        width: 100%;
        padding: 0 30px;
        box-sizing: border-box;
    }
</style>
