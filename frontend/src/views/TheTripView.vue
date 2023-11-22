<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter();
import SelectedItem from '@/components/map/SelectedItem.vue';
import AttractionItem from '@/components/map/AttractionItem.vue';

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
      positions.value.clear();
      data.data.content.map((data) => {
        positions.value.set(data.id, data);
      });
      loadMarkers();
    },
    (error) => {
      console.log('query failed', error);
    }
  );
};

var map;
const positions = ref(new Map());
const markers = ref(new Map());
const infowindows = ref(new Map());
const selectedItems = ref(new Map());
const center = ref('');
const numberMarkers = ref([]);
const polyLine = ref();

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

  numberMarkers.value = [
    new kakao.maps.MarkerImage('https://i.ibb.co/6Fc3LTj/one.png', new kakao.maps.Size(31, 35)),
    new kakao.maps.MarkerImage('https://i.ibb.co/LxHgbJ0/two.png', new kakao.maps.Size(31, 35)),
    new kakao.maps.MarkerImage('https://i.ibb.co/NyTP3Sy/three.png', new kakao.maps.Size(31, 35)),
    new kakao.maps.MarkerImage('https://i.ibb.co/YBNhrgF/four.png', new kakao.maps.Size(31, 35)),
    new kakao.maps.MarkerImage('https://i.ibb.co/xJznY1z/five.png', new kakao.maps.Size(31, 35)),
    new kakao.maps.MarkerImage('https://i.ibb.co/LgGyhNn/six.png', new kakao.maps.Size(31, 35)),
    new kakao.maps.MarkerImage('https://i.ibb.co/F6b3Ygz/seven.png', new kakao.maps.Size(31, 35))
  ];
};

// 마커에 들어갈 인포윈도우 만드는 함수
const makeInfoWindow = (place) => {
  let iwContent = document.createElement('div');
  let title = document.createElement('h3');
  let content = document.createElement('div');

  content.innerText = place.contentType + '\n tel: ' + place.tel + '\n';

  title.appendChild(document.createTextNode(place.title));

  iwContent.appendChild(title);
  iwContent.appendChild(content);

  const infowindow = new kakao.maps.InfoWindow({
    content: iwContent,
    removable: true,
    disableAutoPan: true
  });
  //이미 인포윈도우가 있다면 제거
  if (infowindows.value.get(place.id)) {
    infowindows.value.get(place.id).setMap(null);
  }
  infowindows.value.set(place.id, infowindow);
  return infowindow;
};

// 한 마커의 인포윈도우가 열렸을 때 다른 마커의 인포윈도우를 지우는 함수
const deleteInfoWindows = () => {
  infowindows.value.forEach((window) => {
    window.close();
  });
};

//한 마커를 생성하는 함수
const createMarker = (place, img) => {
  const marker = new kakao.maps.Marker({
    map: map,
    position: new kakao.maps.LatLng(place.latitude, place.longitude),
    title: place.title,
    clickable: true,
    image: img
  });
  const infowindow = makeInfoWindow(place);
  kakao.maps.event.addListener(marker, 'click', () => {
    deleteInfoWindows();
    center.value = new kakao.maps.LatLng(place.latitude, place.longitude);
    infowindow.open(map, marker);
    document.getElementById(place.id).scrollIntoView({ behavior: 'smooth' }, true);
  });
  return marker;
};

// 마커들을 등록하는 함수
const loadMarkers = () => {
  deleteMarkers();

  markers.value = new Map();
  for (const [index, place] of positions.value) {
    const marker = createMarker(place);
    markers.value.set(place.id, marker);
  }

  var bounds = new kakao.maps.LatLngBounds();

  for (const [index, place] of positions.value) {
    bounds.extend(new kakao.maps.LatLng(place.latitude, place.longitude));
  }
  map.setBounds(bounds);
};

// 마커를 지우는 함수
const deleteMarkers = () => {
  if (markers.value.length > 0) {
    for (const [index, marker] of markers.value) {
      marker.setMap(null);
    }
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
    closeItem(item.id);
  }
};
// emit from selectedItem
// 셀렉션에서 제거
const closeItem = (id) => {
  markers.value.get(id).setMap(null);
  const marker = createMarker(selectedItems.value.get(id));
  markers.value.set(id, marker);
  selectedItems.value.delete(id);
};

// 선택된 아이템들에 대해 번호 부여하기
// 선택된 아이템들에 대해 선 긋기
watch(
  () => {
    return selectedItems.value;
  },
  (newVal) => {
    let number = 0;
    let paths = [];
    for (let [index, val] of newVal) {
      markers.value.get(index).setMap(null);
      const marker = createMarker(val, numberMarkers.value[number++]);
      markers.value.set(index, marker);
      paths.push(new kakao.maps.LatLng(val.latitude, val.longitude));
    }
    if (polyLine.value !== undefined) polyLine.value.setMap(null);
    polyLine.value = new kakao.maps.Polyline({
      map: map,
      endArrow: true,
      path: paths,
      strokeColor: '#00CC00'
    });
  },
  { deep: true }
);
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
        <AttractionItem
          v-for="[index, position] of positions"
          :key="position.id"
          :position="position"
          @select-or-not="selectOrNot"
          :id="position.id"
        ></AttractionItem>
      </div>
      <div id="map" class="col-9"></div>
    </div>
    <div class="row border border-white bg-white">
      <SelectedItem
        class="col"
        height="50"
        v-for="(value, key) in selectedItems"
        :key="key"
        :selectedItem="value"
        @close-item="closeItem"
      ></SelectedItem>
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
