<template>

    <div class="shoppingcart">
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/mainpage', }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>我的订单</el-breadcrumb-item>
            <el-breadcrumb-item>购物车</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card class="card" style="transform: translate(5%,10%)">
            <el-table
                    ref="multipleTable"
                    :data="shoppingCartList"
                    tooltip-effect="dark"
                    style="width: 100%"
                    row-key="id"
            >

                <el-table-column
                        reserve-selection
                        width="85">
                    <template slot-scope="scope">
                    <el-checkbox v-model="scope.row.selected" true-label="true" false-label="false" @change="handleSelectionChange(scope.row.id,scope.row.selected)">

                    </el-checkbox></template>
                </el-table-column>
                <el-table-column
                        width="150"
                        reserve-selection>
                    <template slot-scope="scope">
                        <img :src="scope.row.url" width="70" height="70" class="head_pic"/>
                    </template>
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="商品名称"
                        reserve-selection
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="price"
                        label="单价"
                        reserve-selection
                        width="100">
                </el-table-column>
                <el-table-column

                        label="数量"
                        reserve-selection
                        width="250">
                    <el-input-number @change="changeNum(scope.row.id,scope.row.num)" slot-scope="scope"
                                     class="el-input-num" v-model="scope.row.num" :min="1"
                                     :max="scope.row.stock" label="购物车数量"
                                     style="margin-right: 70px"></el-input-number>
                </el-table-column>
                <el-table-column
                        prop="totalPriceForEach"
                        reserve-selection
                        label="总价"
                        width="180">
                </el-table-column>
                <el-table-column label="操作" width="150">
                    <template slot-scope="scope">
                    <el-link type="danger" @click="deleteCart(scope.row.id)" >删除</el-link>
                    </template>
                </el-table-column>
            </el-table>
            <el-row>
                <div class="txt it" style="margin-top: 25px">{{'总价: RMB '+this.totalPrice}}</div>
                <el-button type="primary" class="btngo" round @click="submmit">
                    去结算
                </el-button>
            </el-row>

        </el-card>

    </div>
</template>


<script>
    export default {
        created() {
            this.unitPage();
        },


        data() {
            return {
                // queryInfo: {
                    currentId: window.sessionStorage.getItem('id'),
                    // currentMenuId: '',
                    // query:'',
                    // unitnum: '',
                // },
                shoppingCartList: [],
                totalPrice: '',
                unitnum: 1,
                multipleSelection: [],
            }
        },
        methods: {
            async unitPage() {
                const {data: res} = await this.$http.get("cartMessage/"+this.currentId);//todo
                this.shoppingCartList = res.data;
                this.totalPrice = res.total;
            },
            async changeNum(id, num) {
                // this.queryInfo.currentMenuId = id;
                // this.queryInfo.unitnum = num;
                const {data: res} = await this.$http.put("AddOrSubCarts?currentmenuid="+id+"&unitnum="+num);//todo
                if (res == "success") {
                    this.unitPage();
                }
            },
            async handleSelectionChange(id,slctd) {
                // this.queryInfo.currentMenuId=id;
                // this.queryInfo.query=slctd;
                const {data: res} = await this.$http.put("updateSelected?currentmenuid="+id+"&query="+slctd);//todo
                if(res=="success"){
                    this.unitPage();
                }
            },
            async deleteCart(id){
                const confirmResult = await this.$confirm(
                    "此操作将从购物车中移除该商品, 是否继续?",
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
                // this.queryInfo.currentMenuId=id;
                const {data: res} = await this.$http.delete("deleteCarts/"+id);//todo
                if (res != "success") {
                    return this.$message.error("操作失败！！！");
                }
                this.$message.success("操作成功！！！");
                this.unitPage();
            },

            async submmit(){
                const confirmResult = await this.$confirm(
                    "确认提交?",
                    "提示",
                    {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning"
                    }
                ).catch(err => err);
                // 成功删除为confirm 取消为 cancel
                if (confirmResult != "confirm") {
                    return this.$message.info("已取消");
                }
                const {data: res} = await this.$http.post("submmit/"+this.currentId);//todo
                if(res=="success"){
                    this.unitPage();
                    return this.$message.success("下单成功！！！");
                }else{
                    return this.$message.error("下单失败！！！");
                }
            },

        },
    }


</script>

<style lang="less">
    .txt {
        font-size: 18px;
        text-align: left;
    }

    .it {
        margin-bottom: 3px;
    }

    .card {
        width: 90%;
        margin-bottom: 9px;
        padding: 10px;
    }


    .btngo {
        position: fixed;
        right: 30px;
        bottom: 30px;
        border: 10px;
        margin-top: 100px;
    }
</style>
