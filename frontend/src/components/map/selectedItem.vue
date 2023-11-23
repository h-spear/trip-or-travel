<script setup>
import { onMounted, onUpdated, watch, ref } from 'vue';
const props = defineProps({
  selectedItem: Object
});

const emits = defineEmits(['unselectItem']);
const unselectItem = () => {
  emits('unselectItem', props.selectedItem);
};

onMounted(() => {
  console.log('onMounted', props.selectedItem);
});
</script>

<template>
  <div class="attraction-card">
    <div class="image-box">
      <img
        src="@/assets/image/no-picture.png"
        v-if="selectedItem.imageUrl == ''"
        style="height: 100%; width: 100%; border-radius: 10px"
        alt="..."
      />
      <img
        :src="selectedItem.imageUrl"
        v-if="selectedItem.imageUrl != ''"
        style="height: 100%; width: 100%; border-radius: 10px"
        alt="..."
      />
    </div>
    <div class="info-box">
      <h5 class="info-title">{{ selectedItem.title }}</h5>
      <p style="margin: 0; font-weight: 700; font-size: 16px">{{ selectedItem.contentTypeName }}</p>
      <p style="margin: 5px 0 0 0; overflow: hidden; text-overflow: ellipsis; white-space: nowrap">
        {{ selectedItem.addr1 }} {{ selectedItem.addr2 }}
      </p>
    </div>
    <!-- <p @click="unselectItem" class="card-btn" emit="change">
      <font-awesome-icon icon="fa-solid fa-xmark" />
    </p> -->
  </div>
</template>

<style scoped>
.attraction-card {
  background: #ffffff80;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 10px;
  width: 100%;
  padding: 10px;
  display: flex;
  align-content: center;
  margin-bottom: 20px;
  position: relative;
}

.image-box {
  width: 100px;
  height: 100px;
}

.info-box {
  display: flex;
  flex-direction: column;
  padding: 5px 15px;
  width: 300px;
  align-content: center;
  justify-content: center;
}

.info-title {
  height: 30px;
  overflow: hidden;
  font-size: 18px;
  font-weight: 600;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin: 0;
}

.info-btnBox {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card-btn {
  font-size: 40px;
  width: 40px;
  height: 40px;
  line-height: 0;
  text-align: center;
  position: absolute;
  right: 10px;
  transition: 0.3s;
  cursor: pointer;
}

.card-btn:hover {
  color: red;
}
</style>
