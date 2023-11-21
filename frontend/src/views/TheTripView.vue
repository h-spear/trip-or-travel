<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();
import selectedItem from '@/components/map/selectedItem.vue';
import attractionItem from '@/components/map/attractionItem.vue';

import { getAttraction } from '@/api/attraction.js';

const selectedSido = ref(1);
const selectedGugun = ref(1);
const selectedContentType = ref(12);

import VSelect from '@/components/common/VSelect.vue';
import { AddressStore } from '@/stores/AddressStore.js';
const addressStore = AddressStore();
const { sidos, gugunBySido } = addressStore;
const guguns = ref([{ text: '', value: '' }]);
const contentTypes = ref([
  { value: '12', text: '관광지' },
  { value: '14', text: '문화시설' },
  { value: '15', text: '축제공연행사' },
  { value: '25', text: '여행코스' },
  { value: '28', text: '레포츠' },
  { value: '32', text: '숙박' },
  { value: '38', text: '쇼핑' },
  { value: '39', text: '음식점' }
]);
const selectSido = (data) => {
  guguns.value = gugunBySido[data];
  selectedSido.value = data;
};
const selectGugun = (data) => {
  selectedGugun.value = data;
};
const selectContentType = (data) => {
  selectedContentType.value = data;
};
const searchByDb = () => {
  const query = {
    sidoCode: selectedSido.value,
    gugunCode: selectedGugun.value,
    contentTypeId: selectedContentType.value
  };

  getAttraction(
    query,
    ({ data }) => {
      positions.value = [];
      data.data.content.map((data) => {
        positions.value.push(data);
      });
      loadMarkers();
    },
    (error) => {
      console.log('query failed', error);
    }
  );
};

var map;
const positions = ref([]);
const markers = ref([]);
const infowindows = ref(new Map());
const selectedItems = ref(new Map());
const center = ref('');
// 강남구 : 1, 1

// 들어갈 아이템은 무슨 정보들을 가져야 하는가?
// 고유 id(디비 기준. if 사용자 고유 있을 시 그걸 고유 id로), 좌표xy,

// const props = defineProps({ stations: Array, selectStation: Object });

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement('script');
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById('map');
  // 위치는 나중에 사용자 주소 기반으로 설정하자
  center.value = new kakao.maps.LatLng(33.450701, 126.570667);
  const options = {
    center: center.value,
    level: 3
  };
  map = new kakao.maps.Map(container, options);

  // 지도 컨트롤러 생성
  map.addControl(new kakao.maps.MapTypeControl(), kakao.maps.ControlPosition.RIGHT);
  map.addControl(new kakao.maps.ZoomControl(), kakao.maps.ControlPosition.RIGHT);

  // 지도 클릭 이벤트를 등록한다 (좌클릭 : click, 우클릭 : rightclick, 더블클릭 : dblclick)
  kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
    console.log('지도에서 클릭한 위치의 좌표는 ' + mouseEvent.latLng.toString() + ' 입니다.');
  });
};

// 마커에 들어갈 인포윈도우 만드는 함수
const makeInfoWindow = (place) => {
  let iwContent = document.createElement('div');
  let title = document.createElement('h3');
  let content = document.createElement('div');
  // };

  content.innerText = place.contentType + '\n tel: ' + place.tel + '\n';

  title.appendChild(document.createTextNode(place.title));

  iwContent.appendChild(title);
  iwContent.appendChild(content);

  const infowindow = new kakao.maps.InfoWindow({
    content: iwContent,
    removable: true,
    disableAutoPan: true
  });
  infowindows.value.set(place.id, infowindow);
  return infowindow;
};

// 한 마커의 인포윈도우가 열렸을 때 다른 마커의 인포윈도우를 지우는 함수
const deleteInfoWindows = () => {
  infowindows.value.forEach((window) => {
    window.close();
  });
};

// 마커를 등록하는 함수
const loadMarkers = () => {
  deleteMarkers();

  // const imgSrc = require("@/assets/map/markerStar.png");
  // const imgSize = new kakao.maps.Size(24, 35);
  // const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((place) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      // position: position.latlng, // 마커를 표시할 위치
      position: new kakao.maps.LatLng(place.latitude, place.longitude), // 마커를 표시할 위치
      title: 'test', // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });

    // kakao.maps.event.addListener(marker, 'mouseover', function() {
    //     console.log('마커에 mouseover 이벤트가 발생했습니다!', marker);
    // });

    // // 마커에 mouseout 이벤트 등록
    // kakao.maps.event.addListener(marker, 'mouseout', function() {
    //     console.log('마커에 mouseout 이벤트가 발생했습니다!');
    // });

    const infowindow = makeInfoWindow(place);

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', () => {
      deleteInfoWindows();
      center.value = new kakao.maps.LatLng(place.latitude, place.longitude);
      infowindow.open(map, marker);
      document.getElementById(place.id).scrollIntoView({ behavior: 'smooth' }, true);
    });

    markers.value.push(marker);
  });

  var bounds = new kakao.maps.LatLngBounds();
  positions.value.map((data) => {
    bounds.extend(new kakao.maps.LatLng(data.latitude, data.longitude));
  });
  map.setBounds(bounds);
};

// 마커를 지우는 함수
const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
    deleteInfoWindows();
    infowindows.value.clear();
  }
};

// 중심 변화 시 중심으로 이동시켜주는 watch
// 값을 카카오 latlng로 받기
watch(
  () => center.value,
  () => {
    map.panTo(center.value);
  },
  { deep: true }
);

// 다음 페이지로 넘어가는 함수
const moveDetail = () => {
  console.log('before', selectedItems.value);
  const stateItem = [];
  selectedItems.value.forEach((item) => {
    stateItem.push(JSON.parse(JSON.stringify(item)));
  });
  console.log('mapped after', stateItem);
  router.push({
    name: 'trip-detail',
    state: {
      tmp: 1,
      selectedItems: stateItem
    }
  });
};

// emit from attractionItem
// 셀렉션에 등록
const selectOrNot = (clicked, item) => {
  item.isSelected = !item.isSelected;
  if (clicked) {
    selectedItems.value.set(item.id, item);
  } else {
    selectedItems.value.delete(item.id);
  }
};
// emit from selectedItem
// 셀렉션에서 제거
const closeItem = (id) => {
  selectedItems.value.delete(id);
};
</script>

<template>
  <div class="container-fluid border border-info">
    <div class="row border border-white border-3 bg-black">
      <form class="col-6" action="" @submit.prevent="searchByDb">
        <VSelect :selectOptions="sidos" @onKeySelect="selectSido"></VSelect>
        <VSelect :selectOptions="guguns" @onKeySelect="selectGugun"></VSelect>
        <VSelect :selectOptions="contentTypes" @onKeySelect="selectContentType"></VSelect>
        <button type="submit">검색</button>
      </form>
      <form class="col-6" action="" @submit.prevent="searchByKakao">
        <button type="button">검색</button>
      </form>
    </div>
    <div class="row border border-dark border-2">
      <div id="marker-list" class="col-3 bg-white">
        <attractionItem
          v-for="position in positions"
          :key="position.id"
          :position="position"
          @select-or-not="selectOrNot"
          :id="position.id"
        ></attractionItem>
      </div>
      <div id="map" class="col-9"></div>
    </div>
    <div class="row border border-white bg-white">
      <selectedItem
        class="col"
        v-for="(value, key) in selectedItems"
        :key="key"
        :selectedItem="value"
        @close-item="closeItem"
      ></selectedItem>
      <button class="btn btn-primary" @click="moveDetail">저장</button>
    </div>
  </div>
</template>

<style>
#map {
  /* width: 500px; */
  height: 500px;
}
#marker-list {
  height: 500px;
  overflow: scroll;
}
</style>
