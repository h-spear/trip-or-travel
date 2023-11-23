<script setup>
import { onMounted } from 'vue';
import TheHeadingNavbar from '@/components/layout/TheHeadingNavbar.vue';
import { AddressStore } from '@/stores/AddressStore.js';
const addressStore = AddressStore();
const { sidos, funcGetItems } = addressStore;

(function () {
  var w = window;
  if (w.ChannelIO) {
    return w.console.error('ChannelIO script included twice.');
  }
  var ch = function () {
    ch.c(arguments);
  };
  ch.q = [];
  ch.c = function (args) {
    ch.q.push(args);
  };
  w.ChannelIO = ch;
  function l() {
    if (w.ChannelIOInitialized) {
      return;
    }
    w.ChannelIOInitialized = true;
    var s = document.createElement('script');
    s.type = 'text/javascript';
    s.async = true;
    s.src = 'https://cdn.channel.io/plugin/ch-plugin-web.js';
    var x = document.getElementsByTagName('script')[0];
    if (x.parentNode) {
      x.parentNode.insertBefore(s, x);
    }
  }
  if (document.readyState === 'complete') {
    l();
  } else {
    w.addEventListener('DOMContentLoaded', l);
    w.addEventListener('load', l);
  }
})();

ChannelIO('boot', {
  pluginKey: 'b1c0db0c-a76b-4219-a7ba-8059360f9001' // fill your plugin key
});

onMounted(() => {
  if (!sidos.value) {
    funcGetItems();
  }
});
</script>

<template>
  <div style="display: flex; justify-content: center; align-items: center">
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
  </div>
</template>

<style>
body {
  background-image: url(assets/bg.jpg);
  background-size: cover; /* 이미지를 화면에 꽉 채우도록 크기 조정 */
  background-repeat: no-repeat; /* 배경 이미지 반복 없음 */
  background-attachment: fixed; /* 스크롤 시 배경 이미지 고정 */
}

a {
  cursor: pointer;
}

#profileImg {
  margin: 10px;
  width: 50px;
  height: 50px;
}
</style>
