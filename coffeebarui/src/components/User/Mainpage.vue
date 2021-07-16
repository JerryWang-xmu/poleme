<template>
    <el-container>
        <el-header>
            <el-carousel class="main_window" :interval="4000" type="card" height="500px">
                <el-carousel-item v-for="item in imgList" :key="item.id">
                    <el-row>
                        <el-col :span="24"><img ref="imgHeight" :src="item.idView" class="banner_img"/></el-col>
                    </el-row>
                </el-carousel-item>

            </el-carousel>
        </el-header>
        <el-main>
            <div class="main_welcome">
                <h3>精彩从这里开始</h3>
            </div>
        </el-main>
    </el-container>


</template>

<style lang="less" scoped>
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 800px;
        margin: 0;
        /*padding: 500px 500px;*/
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
        height: 100%;
        /*padding: 500px 500px;*/
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
        height: 100%;
        /*padding: 500px 500px;*/
    }

    .main_welcome {
        color: black;
        position: absolute;
        left: 55%;
        top: 94%;
        transform: translate(-50%, -50%);
    }

    .main_window {
        transform: translate(0, 5%);
    }

</style>
<script>
    export default {
        data() {
            return {
                // 图片需要引入, 否则无法显示
                imgList: [
                    {id: 0, idView: require('../../assets/t4.png')},
                    {id: 1, name: '', idView: require('../../assets/t5.png')},
                    {id: 2, name: '', idView: require('../../assets/t1.png')},
                    {id: 3, name: '', idView: require('../../assets/t3.png')},
                    {id: 4, name: '', idView: require('../../assets/t2.png')},
                ]
            }
        }
    }
</script>