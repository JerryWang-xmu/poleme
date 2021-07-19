<template>
    <div>

        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: detailInfo.path, }">{{detailInfo.style}}</el-breadcrumb-item>
            <el-breadcrumb-item>{{detailInfo.name}}</el-breadcrumb-item>
        </el-breadcrumb>

        <el-row :gutter="2">
            <el-col :span="12" style="padding-left: 80px">
                <el-card :body-style="{ padding: '10px' }">
                    <img :src="detailInfo.imgurl" class="image">
                </el-card>
            </el-col>
            <el-col :span="12">
                <div class="description" @mouseleave="mouseLeave" @mouseover="mouseOver" :style="active">
                    <el-card shadow="hover" class="box-card">
                        <div slot="header">
                            <h2>{{detailInfo.name}}</h2>
                            <h3>售价：RMB {{detailInfo.price}}</h3>
                            <span>当前库存：{{detailInfo.stock}}</span>
                        </div>
                        <div class="text item">
                            <span>{{detailInfo.description}}</span>
                        </div>


                        <div class="btnss">
                            <el-input-number class="el-input-num" v-model="infoForm.unitnum" :min="1"
                                             :max="detailInfo.stock" label="购物车数量"
                                             style="margin-right: 70px"></el-input-number>
                            <el-tooltip effect="dark" content="加入购物车" placement="top-start" :enterable="false">
                                <el-button @click="shoppingcart" style="margin-right: 30px" type="primary"
                                           icon="el-icon-shopping-cart-full" circle></el-button>
                            </el-tooltip>
                            <el-tooltip effect="dark" content="立即付款" placement="top-start" :enterable="false">
                                <el-button @click="payment(detailInfo.price)" type="success" icon="el-icon-check" circle></el-button>
                            </el-tooltip>
                        </div>
                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
<style>
    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: -50px;
    }

    .box-card {
        width: 480px;
    }

    .image {
        width: 100%;
        display: block;
    }

    .el-card {
        width: 60%;
        height: 50%;
        padding: 5%;
    }

    .btnss {
        display: flex;
        justify-content: flex-end;
        padding-top: 120px;
    }

    .description {
        transform: translate(0%, 0%);
        background-color: #eaedf1;
    }
</style>

<script>
    export default {
        created() {
            this.getInformation();
        },
        data() {
            return {

                active: '',
                currentDate: new Date(),
                activeNames: ['1'],

                // infoForm: {
                    currentMenuId: window.sessionStorage.getItem('menuId'),
                    currentId: window.sessionStorage.getItem('id'),
                    unitnum: 1,
                // },


                infoForm: {
                currentMenuId: window.sessionStorage.getItem('menuId'),
                currentId: window.sessionStorage.getItem('id'),
                unitnum: 1,
                },
                detailInfo: '',
                payForm:{
                    userid: window.sessionStorage.getItem("id"),
                    menuid: window.sessionStorage.getItem('menuId'),
                    unitnum:'',
                    unitprice:'',
                },
            };
        },
        methods: {
            mouseOver() {
                this.active = 'transform: translate(0%,1%)';
            },
            mouseLeave() {
                this.active = '';
            },
            async getInformation() {
                const {data: res} = await this.$http.get("id2menu/"+this.currentMenuId+"/"+this.currentId+"/"+this.unitnum);
                if (res.flag == "ok") {
                    this.detailInfo = res.data;
                } else {
                    this.$message.error("查询菜单失败，请重试！");
                }
            },

            async shoppingcart() {
                const {data: res} = await this.$http.post("shoppingcart", this.infoForm);
                if (res == "success") {
                    this.$message.success("成功加入购物车！");
                }else{
                    this.$message.error("添加购物车失败！");
                }
            },
            async payment(price) {
                this.payForm.unitnum=this.infoForm.unitnum;
                this.payForm.unitprice=price;
                console.log(this.payForm);
                const confirmResult = await this.$confirm(
                    "此操作立即购买菜品, 是否继续?",
                    "提示",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "information"
                    }
                ).catch(err => err);
                // 成功为confirm 取消为 cancel
                if (confirmResult != "confirm") {
                    return this.$message.info("已取消购买");
                }
                const {data: res} = await this.$http.post("paymenu", this.payForm);
                if (res != "success") {
                    return this.$message.error("购买失败！！！");
                }
                this.getInformation();
                this.$message.success("购买成功！！！");
            },
        }
    }
</script>
