<template>
    <div class="register_container">
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
                    ref="registerFormRef"
                    :model="registerForm"
                    :rules="registerRules"
                    class="register_form"
                    label-width="0px"
            >
                <el-form-item prop="title">
                    <h1 align=center>Welcome Poleme!</h1>
                </el-form-item>
                <el-form-item prop="username" style="margin: 20px 50px 20px 50px">
                    <el-input @blur="blurUsername" v-model="registerForm.username" prefix-icon="el-icon-user"
                              placeholder="用户名"></el-input>
                </el-form-item>
                <el-form-item prop="password" style="margin: 20px 50px 20px 50px">
                    <el-input v-model="registerForm.password" prefix-icon="el-icon-unlock" type="password"
                              placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item prop="phone" style="margin: 20px 50px 20px 50px">
                    <el-input v-model="registerForm.phone" prefix-icon="el-icon-phone"
                              placeholder="联系电话"></el-input>
                </el-form-item>
                <el-form-item prop="email" style="margin: 20px 50px 20px 50px">
                    <el-input v-model="registerForm.email" prefix-icon="el-icon-message"
                              placeholder="电子邮箱"></el-input>
                </el-form-item>

                <!--                添加登录和重置按钮-->
                <el-form-item class="btns">
                    <el-button type="primary" @click="addUserByRegister">提交</el-button>
                    <el-button type="info" @click="resetRegisterForm">重置</el-button>
                </el-form-item>
                <el-link class="el-l" type="primary" :underline="false" @click="backToLogin">返回登录页面</el-link>
            </el-form>
        </div>
    </div>
</template>


<script>

    export default {
        data() {
            return {
                registerForm: {
                    username: "",
                    password: "",
                    phone: "",
                    email: "",
                },
                registerRules: {
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
                    phone: [
                        {required: true, message: '联系电话为必填项', trigger: 'blur'},
                        {min: 3, max: 11, message: '长度在3~11个字符之间', trigger: 'blur'},
                    ],
                    email: [
                        {required: true, message: '电子邮箱称为必填项', trigger: 'blur'},
                        {min: 5, max: 20, message: '长度在5~30个字符之间', trigger: 'blur'},
                    ],
                },
            }
        },
        methods: {
            resetRegisterForm() {
                this.$refs.registerFormRef.resetFields();
            },
            backToLogin() {
                this.$router.push({path: "/login"});
            },
            async blurUsername() {
                const {data: res} = await this.$http.post("registername", this.registerForm);
                if (res == "error") {
                    this.$message.error("该用户名已被注册！请尝试输入新的用户名！");

                }
            },
            addUserByRegister() {
                this.$refs.registerFormRef.validate(async valid => {
                    if (!valid) {
                        return;
                    }
                    const {data: res} = await this.$http.post("register", this.registerForm);
                    if (res == "error") {
                        this.$message.error("该用户名已被注册！请尝试输入新的用户名！");
                    } else if (res == "fail") {
                        this.$message.error("注册失败！请重试！");
                    } else {
                        this.$message.success("注册成功！即将为您跳转登录页面！");
                        window.sessionStorage.setItem('usname', this.registerForm.username);
                        this.goGrdoupRecor();
                    }
                })
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
    }
</script>

<style lang="less" scoped>
    .register_container {
        background-color: rgb(110, 98, 85);
        height: 100%;
    }

    .register_box {
        width: 600px;
        height: 500px;
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
        margin-top: 40px;
        display: flex;
        justify-content: flex-end;

    }

    .el-l {
        display: flex;
        justify-content: flex-start;
        transform: translate(50px, -90px);
    }

    .register_form {
        position: absolute;
        bottom: 0%;
        width: 100%;
        padding: 0 30px;
        box-sizing: border-box;
    }
</style>