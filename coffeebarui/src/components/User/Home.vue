<template>
    <!--引入container布局-->
    <el-container class="home-container">
        <!--头部-->
        <el-header :height="'104px'">
            <div>
                <img src="../../assets/caffeebar.png" alt class="logo_img"/>
                <span> 泼了么咖啡</span>
            </div>
            <el-dropdown @command="handleCommand">
  <span class="el-dropdown-link">
      <h1>{{uname}}</h1><i class="el-icon-arrow-down el-icon--right"></i>
  </span>
                <el-dropdown-menu class="eld-menu" slot="dropdown">
                    <el-dropdown-item command="account">基本资料</el-dropdown-item>
                    <el-dropdown-item command="password">修改密码</el-dropdown-item>
                    <el-dropdown-item command="cancel" v-show="showItem">注销</el-dropdown-item>
                    <el-dropdown-item command="quit" divided>退出</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </el-header>
        <!--主体-->
        <el-container>
            <!--侧边栏-->
            <el-aside :width="isCollapse ?'64px':'168px'">
                <div class="toggle-button" @click="toggleCollapase">|||</div>
                <!--一下是背景颜色，字体，选中后颜色-->
                <el-menu background-color="#545c64" text-color="#fff" active-text-color="#409eff" :collapse="isCollapse"
                         :collapse-transition="false" :router="true" :default-active="activePath">
                    <!--一级菜单-->
                    <el-submenu :index="item.id+''" v-for="item in menuList" :key="item.id">
                        <template slot="title">

                            <i :class="iconsObject[item.id]"></i>
                            <span>{{item.title}}</span>
                        </template>
                        <!--二级菜单-->
                        <el-menu-item :index="it.path" v-for="it in item.sList" :key="it.id"
                                      @click="saveNavState(it.path)">
                            <template slot="title">
                                <span>{{it.title}}</span>
                            </template>
                        </el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-aside>
            <!--主体内容-->
            <el-main>
                <!--              路由占位符-->
                <router-view>
                </router-view>
            </el-main>
        </el-container>
    </el-container>
</template>
<script>
    export default {
        inject: ['reload'],
        data() {
            return {
                queryInfo: {
                    currentId: window.sessionStorage.getItem('id'),
                },
                uname: window.sessionStorage.getItem('usname'),
                //菜单列表
                menuList: [],//动态导航
                isCollapse: true,//伸缩属性
                showItem: true,
                iconsObject: {
                    '100': 'el-icon-s-home',
                    '200': 'el-icon-s-custom',
                    '300': 'el-icon-s-order',
                    '400': 'el-icon-s-goods',
                    '1000': 'el-icon-pie-chart',
                    '1100': 'el-icon-edit-outline',
                    '1200': 'el-icon-s-order',
                },
                activePath: '/mainpage',
            };

        },
        //onload事件
        created() {
            this.getMenuList();
            this.activePath = window.sessionStorage.getItem('activePath');
        },
        methods: {

            handleCommand(command) {
                if (command == "quit") {
                    window.sessionStorage.clear();
                    this.$router.push("/login");
                } else if (command == "cancel") {
                    window.sessionStorage.clear();
                    this.reload();
                    window.sessionStorage.setItem('id', "null");

                } else if (command == "account") {
                    this.$router.push({path: "/information"});
                } else if (command == "password") {
                    this.$router.push({path: "/secret"});
                }
            },


            // 切换菜单折叠与展开
            toggleCollapase() {
                this.isCollapse = !this.isCollapse;
            },

            saveNavState(activePath) {
                window.sessionStorage.setItem('activePath', activePath);
                this.activePath = activePath;
            },

            //获取导航菜单方法
            async getMenuList() {
                console.log(this.currentId);
                const {data: res} = await this.$http.post("menus", this.queryInfo);
                if (res.status != 200) return this.$message.error("操作失败！！！");
                this.menuList = res.data;
            },
        }
    };
</script>
<style lang='less' scoped>
    .el-dropdown-link {
        cursor: pointer;
        color: #409EFF;
    }

    .el-icon-arrow-down {
        font-size: 12px;
    }

    //头样式
    .el-header {
        background-color: rgb(110, 98, 85);
        display: flex;
        justify-content: space-between; // 左右贴边
        padding-left: 0%; // 左边界
        align-items: center; // 水平
        color: #fff;
        font-size: 20px;

        > div {
            //左侧div加布局
            display: flex;
            align-items: center;

            span {
                margin-left: 15px;
            }
        }
    }

    //侧边
    .el-aside {
        background-color: rgb(110, 98, 85);

        .el-menu {
            border-right: none; // 对其右边框
        }
    }

    //主体
    .el-main {
        background-color: #eaedf1;
        border-radius: 4px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    }

    .home-container {
        height: 100%;
    }

    //图片
    .logo_img {
        padding: 20px 5px;
        width: 55px;
        height: 55px;
    }

    //按钮样式
    .toggle-button {
        background-color: #4a5064;
        font-size: 10px;
        line-height: 24px;
        color: #fff;
        text-align: center;
        letter-spacing: 0.2em;
        cursor: pointer; // 显示鼠标指针为：小手
    }
</style>