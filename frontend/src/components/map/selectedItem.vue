<script setup>
import { onMounted, onUpdated } from "vue";
const props = defineProps({
    selectedItems:Array
})
onUpdated(() => {
    console.log('updated',props.selectedItems)
    props.selectedItems.forEach((data)=>{
        console.log('each', data)
    })

})
</script>

<template>
    <div>
        <div v-for="marker in selectedItems" :key="marker.id">
        {{ marker }}
        </div>
    </div>
</template>

<style scoped>

</style>