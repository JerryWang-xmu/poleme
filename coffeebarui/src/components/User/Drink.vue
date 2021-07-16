<template>

    <div class="menu_drink">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>菜单</el-breadcrumb-item>
            <el-breadcrumb-item>饮料</el-breadcrumb-item>
        </el-breadcrumb>

        <el-row :gutter="40">
            <el-col :span="6" v-for="item in imgList" :key="item.id" style="margin-bottom:100px">
                <div class="avatar_box" @mouseover="mouseOver" @mouseleave="mouseLeave">
                    <!--小图标-->
                    <img :src="item.imgurl" @click="jump(item.id)" alt/>
                    <h1 align=center>{{item.name}}</h1>
                </div>
            </el-col>
        </el-row>
        <el-button @click="drawer = true" type="primary" class="btnmenu" round>
            搜索菜单
        </el-button>
        <el-drawer
                title="菜单"
                :visible.sync="drawer"
                :with-header="false"
                size="38%">

            <el-input placeholder="请输入搜索内容" v-model="queryForm.query" style="padding: 50px" suffix-icon="el-icon-search" @input="textChanged">
            </el-input>
            <el-row :gutter="40">
                <el-col :span="12" v-for="item in queryList" :key="item.id" style="margin-bottom:70px">
                    <div class="avatar_small" @mouseover="mouseOver" @mouseleave="mouseLeave">
                        <!--小图标-->
                        <img :src="item.imgurl" @click="jump(item.id)" alt/>
                        <h1 align=center>{{item.name}}</h1>
                    </div>
                </el-col>
            </el-row>
        </el-drawer>
    </div>
</template>


<script>
    export default {
        data() {
            return {
                drawer: false,
                queryForm: {
                    query: '',
                },
                queryInfo: {
                    currentId: window.sessionStorage.getItem('activePath'),
                },

                imgList: [],
                queryList: [],
            }
        },
        created() {
            this.getList();
        },

        methods: {

            async getList() {
                const {data: res} = await this.$http.post("foodmenu", this.queryInfo);
                if (res.status == "error") return this.$message.error("操作失败！！！");
                this.imgList = res.data;
            },

            jump(identity) {
                window.sessionStorage.setItem('menuId', identity);
                this.$router.push('/goods');
            },
            mouseOver(event) {
                let _el = event.currentTarget;
                this.active = 'transform: translate(0%, -5%)';
                _el.setAttribute('style', this.active);
            },
            mouseLeave(event) {
                let _el = event.currentTarget;
                this.active = '';
                _el.setAttribute('style', this.active);
            },
            async textChanged() {
                const {data: res} = await this.$http.post("selectMenu", this.queryForm);
                if (res.flag == "ok") {
                    this.queryList = res.data;
                }
            },
        }
    }
</script>
<style lang="less" scoped>
    .el-row {
        padding-top: 90px;
        padding-left: 100px;
    }

    .avatar_box {
        width: 130px;
        height: 130px;
        border: 1px solid #eee;
        border-radius: 50%;
        padding: 10px;
        box-shadow: 0 0 10px #ddd;
        /*position: absolute;*/
        left: 50%;
        /*transform: translate(-50%, -50%);*/
        background-color: #ffffff;
        cursor: pointer;

        img {
            width: 100%;
            height: 100%;
            border-radius: 50%;
        }
    }

    .btnmenu {
        position: fixed;
        right: 30px;
        bottom: 30px;
        border: 10px;
    }


    .el-drawer__body {
        width: 100%;
        height: 100%;
        overflow: auto;
    }

    .el-drawer__container ::-webkit-scrollbar {
        display: none;
    }

    .avatar_small {
        width: 110px;
        height: 110px;
        /*position: absolute;*/
        left: 50%;
        cursor: pointer;

        img {
            width: 100%;
            height: 100%;
        }
    }
</style>

<style>
    .el-drawer {
        overflow: auto;
        overflow-x: hidden;
    }
</style>