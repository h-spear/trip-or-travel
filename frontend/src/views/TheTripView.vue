<script setup>
import { ref, watch, onMounted } from 'vue';
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
  console.log('selected sido', gugunBySido[data]);
  guguns.value = gugunBySido[data];
  selectedSido.value = data;
};
const selectGugun = (data) => {
  console.log('selected gugun', data);
  selectedGugun.value = data;
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
const selectedItems = ref(new Map());
// 강남구 : 1, 1

// 들어갈 아이템은 무슨 정보들을 가져야 하는가?
// 고유 id(디비 기준. if 사용자 고유 있을 시 그걸 고유 id로), 좌표xy,

// const props = defineProps({ stations: Array, selectStation: Object });

// watch(
//   () => props.selectStation.value,
//   () => {
//     // 이동할 위도 경도 위치를 생성합니다
//     var moveLatLon = new kakao.maps.LatLng(props.selectStation.lat, props.selectStation.lng);

//     // 지도 중심을 부드럽게 이동시킵니다
//     // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
//     map.panTo(moveLatLon);
//   },
//   { deep: true }
// );
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
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
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

// watch(
//   () => props.stations.value,
//   () => {
//     positions.value = [];
//     props.stations.forEach((station) => {
//       let obj = {};
//       obj.latlng = new kakao.maps.LatLng(station.lat, station.lng);
//       obj.title = station.statNm;

//       positions.value.push(obj);
//     });
//     loadMarkers();
//   },
//   { deep: true }
// );

// 마커에 들어갈 인포윈도우 만드는 함수
const makeInfoWindow = (place) => {
  let iwContent = document.createElement('div');
  let title = document.createElement('h1');
  let content = document.createElement('div');
  let btnWishlist = document.createElement('button');
  let btnSelect = document.createElement('button');

  btnWishlist.innerText = '찜';
  btnWishlist.onclick = () => {
    console.log('wishlist add');
  };
  btnSelect.innerText = '등록';
  btnSelect.onclick = () => {
    console.log('select add');
    place.isSelected = !place.isSelected;
    if (place.isSelected) {
      selectedItems.value.set(place.id, place);
      btnSelect.innerText = '취소';
    } else {
      selectedItems.value.delete(place.id);
      console.log('canceled', selectedItems.value);
      btnSelect.innerText = '등록';
    }
  };

  content.innerText = '들어갈 내용 명세 필요 ddsdsds';
  content.appendChild(btnSelect);
  content.appendChild(btnWishlist);

  title.appendChild(document.createTextNode('제목'));

  iwContent.appendChild(title);
  iwContent.appendChild(content);

  const infowindow = new kakao.maps.InfoWindow({
    content: iwContent,
    removable: true,
    disableAutoPan: true
  });
  return infowindow;
};

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
    kakao.maps.event.addListener(marker, 'click', function () {
      infowindow.open(map, marker);
    });

    markers.value.push(marker);
  });

  var bounds = new kakao.maps.LatLngBounds();
  positions.value.map((data) => {
    bounds.extend(new kakao.maps.LatLng(data.latitude, data.longitude));
  });
  map.setBounds(bounds);
};

//   // 4. 지도를 이동시켜주기
// 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);

// };

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
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
      <div id="map" class="col-9"></div>
      <div id="marker-list" class="col-3 bg-white">
        <attractionItem
          v-for="position in positions"
          :key="position.id"
          :position="position"
        ></attractionItem>
      </div>
    </div>
    <div class="row border border-white bg-white">
      <selectedItem
        v-for="(key, value) of selectedItems"
        :key="key"
        :selectedItems="value"
      ></selectedItem>
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
