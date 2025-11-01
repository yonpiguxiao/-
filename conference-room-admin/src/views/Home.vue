<template>
    <div class="conference-main-layout">
        <div class="conference-main-layout-header">
            <div class="conference-main-layout-nav">
                <Navbar></Navbar>
            </div>
        </div>
    </div>
    <RouterView />
</template>
<script setup>
import Navbar from '@/components/Navbar.vue'
</script>
<style lang="scss" scoped>
.el-main {
    padding: 0;

}

.conference-main-layout {
    padding-top: 20px;
    background-color: #f7f7f7;
    width: 100%;
    height: 100%;
    //position: fixed;


.conference-main-layout-header {
        height: 60px;
        position: absolute;
        width: 100%;
        background: #409eff;
        left: 0;
        top: 0;
        z-index: 3;
        overflow: visible;
    }
    
    .conference-main-layout-nav {
        max-width: 1520px;
        min-width: 100%;
        margin: 0 auto;
        height: 60px;
        background: #fff;
    }

}
</style>
