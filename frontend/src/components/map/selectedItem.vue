<script setup>
import { onMounted, onUpdated } from "vue";
const props = defineProps({
    markers:Array
})
onUpdated(() => {
    console.log('after update')
    props.markers.forEach((data)=>{
        console.log('each', data)
    })

})
</script>

<template>
    <div>
        <div v-for="marker in markers"
        >
        {{ marker.getPosition() }}
        </div>
    </div>
</template>

<style scoped>

</style>