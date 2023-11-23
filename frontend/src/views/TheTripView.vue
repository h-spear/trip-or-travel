<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { localAxios } from '../utils/http-commons';
import { Modal } from 'ant-design-vue';
import { h } from 'vue';

import SelectedItem from '@/components/map/SelectedItem.vue';
import AttractionItem from '@/components/map/AttractionItem.vue';

import { getAttrByType, getAttrByAround } from '@/api/attraction.js';
import { VueDraggableNext } from 'vue-draggable-next';

const local = localAxios();
const router = useRouter();
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

  getAttrByType(
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

const searchByAround = (lat, lng, rad) => {
  console.log(lat, lng, rad);
  getAttrByAround(
    {
      latitude: lat,
      longitude: lng,
      radiusKm: rad
    },
    ({ data }) => {
      console.log('success', data);
      positions.value.clear();
      data.data.map((data) => {
        positions.value.set(data.id, data);
      });
      loadMarkers();
    },
    ({ error }) => {
      console.log('error ', error);
    }
  );
};

var map;
const positions = ref(new Map());
const markers = ref(new Map());
const infowindows = ref(new Map());

const selectedItems = ref([]);
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

const makeInfoWindow = (place) => {
  console.log(place);
};

const info = (contentId) => {
  local
    .get(`attraction/${contentId}`)
    .then(({ data }) => {
      data = data.data;
      console.log(data);
      Modal.info({
        title: `${data.title}`,
        content: h('div', {}, [
          h('p', `${data.addr1} ${data.addr2}`),
          h('p', `${data.overview != null ? data.overview : '설명이 준비되지 않았습니다.'}`)
        ]),
        onOk() {
          console.log('ok');
        }
      });
    })
    .catch((error) => {
      console.log('error : ', error);
    });
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
  kakao.maps.event.addListener(marker, 'click', () => {
    deleteInfoWindows();
    center.value = new kakao.maps.LatLng(place.latitude, place.longitude);
    document.getElementById(place.id).scrollIntoView({ behavior: 'smooth' }, true);
    info(place.id);
  });
  return marker;
};

// 마커들을 등록하는 함수
const loadMarkers = () => {
  deleteMarkers();

  markers.value.clear();
  for (const [index, place] of positions.value) {
    const marker = createMarker(place);
    markers.value.set(place.id, marker);
  }
  for (const ind in selectedItems.value) {
    // console.log('dd', selectedItems.value[ind].id);
    // if (marker.value.get(selectedItems.value[ind].id))
    //   marker.value.get(selectedItems.value[ind].id).setMap(null);
    const marker = createMarker(selectedItems.value[ind], numberMarkers.value[ind]);
    markers.value.set(selectedItems.value[ind].id, marker);
  }

  var bounds = new kakao.maps.LatLngBounds();

  for (const [index, place] of positions.value) {
    bounds.extend(new kakao.maps.LatLng(place.latitude, place.longitude));
  }
  map.setBounds(bounds);
};

// 마커를 지우는 함수
const deleteMarkers = () => {
  if (markers.value.size > 0) {
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
const selectItem = (item) => {
  // markers.value.get(item.id).setMap(null);
  // markers.value.delete(item.id);
  selectedItems.value.push(item);
};
// emit from selectedItem
// 셀렉션에서 제거
const unselectItem = (item) => {
  // console.log(markers.value.get(item.id));
  if (markers.value.get(item.id)) {
    markers.value.get(item.id).setMap(null);
    const marker = createMarker(item);
    markers.value.set(item.id, marker);
  }
  selectedItems.value = selectedItems.value.filter((data) => {
    if (data.id == item.id) return false;
    return true;
  });
};

// 선택된 아이템들에 대해 번호 부여하기
// 선택된 아이템들에 대해 선 긋기
watch(
  () => {
    return selectedItems.value;
  },
  (newVal, oldVal) => {
    console.log('newVal, oldVal', newVal, oldVal);
    let paths = [];
    for (const ind in newVal) {
      if (markers.value.get(newVal[ind].id)) {
        markers.value.get(newVal[ind].id).setMap(null);
        const marker = createMarker(newVal[ind], numberMarkers.value[ind]);
        markers.value.set(newVal[ind].id, marker);
      }
      paths.push(new kakao.maps.LatLng(newVal[ind].latitude, newVal[ind].longitude));
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

const mouseDown = ref(false);
const prevMousePosX = ref(0);
const prevMousePosY = ref(0);
let target = null;
onMounted(() => {
  target = document.querySelector('.draggable-box');
});

const draggableMouseDown = (e) => {
  console.log('draggable mouse down');
  prevMousePosX.value = e.clientX;
  prevMousePosY.value = e.clientY;
  mouseDown.value = true;
};
const draggableMouseMove = (e) => {
  if (mouseDown.value === true) {
    const posX = prevMousePosX.value - e.clientX;
    const posY = prevMousePosY.value - e.clientY;
    prevMousePosX.value = e.clientX;
    prevMousePosY.value = e.clientY;
    target.style.left = target.offsetLeft - posX + 'px';
    target.style.top = target.offsetTop - posY + 'px';
  }
};

const draggableMouseUp = () => {
  console.log('draggable mouse up');
  mouseDown.value = false;
};
</script>

<template>
  <div class="draggable-box">
    <p
      class="draggable-icon"
      @mousedown="draggableMouseDown"
      @mouseup="draggableMouseUp"
      @mousemove="draggableMouseMove"
    >
      <font-awesome-icon icon="fa-solid fa-up-down-left-right" />
    </p>
    <div style="overflow: scroll; height: 440px">
      <VueDraggableNext :list="selectedItems">
        <SelectedItem
          class="col"
          height="50"
          v-for="item in selectedItems"
          :key="item.id"
          :selectedItem="item"
          @unselect-item="unselectItem"
        ></SelectedItem>
      </VueDraggableNext>
    </div>
    <div style="text-align: center; margin-top: 15px">
      <a-button
        type="primary"
        style="width: 100%; font-size: 18px; background-color: rgb(24, 24, 24)"
        size="large"
        @click="moveDetail"
        >여행 계획 만들기</a-button
      >
    </div>
  </div>

  <section>
    <div class="trip-wrapper">
      <div class="search-box">
        <div class="search-elements">
          <div class="search-element">
            <span>시·도</span>
            <VSelect
              :selectOptions="sidos"
              width="200"
              defaultKey="전체"
              @onKeySelect="selectSido"
            ></VSelect>
          </div>

          <div class="search-element">
            <span>구·군</span>
            <VSelect
              :selectOptions="guguns"
              width="200"
              defaultKey="전체"
              @onKeySelect="selectGugun"
            ></VSelect>
          </div>

          <div class="search-element">
            <span>종류</span>
            <VSelect
              :selectOptions="contentTypes"
              width="200"
              defaultKey="전체"
              @onKeySelect="selectContentType"
            ></VSelect>
          </div>
        </div>
        <div>
          <!-- <a-button
            type="primary"
            size="large"
            style="width: 150px; margin: 0 10px"
            @click="filterInit"
            danger
            >필터 초기화</a-button
          > -->
          <a-button
            type="primary"
            size="large"
            style="width: 150px; margin: 0 10px"
            @click="searchByDb"
            >검색</a-button
          >
        </div>
      </div>

      <div class="map-box">
        <div id="marker-list">
          <AttractionItem
            v-for="[index, position] of positions"
            :key="position.id"
            :position="position"
            @select-item="selectItem"
            @unselect-item="unselectItem"
            @search-around="searchByAround"
            :id="position.id"
          ></AttractionItem>
        </div>
        <div id="map" style="width: 100%"></div>
      </div>
    </div>
  </section>
</template>
<style scoped>
section {
  display: flex;
  margin: 0;
  position: relative;
  width: 100vw;
  min-width: 800px;
  max-width: 1400px;
  height: 100%;
  padding: 100px 50px 30px 50px;
}
.trip-wrapper {
  background: #ffffff;
  border-radius: 20px;
  -webkit-box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  box-shadow: 5px 5px 15px 5px rgba(0, 0, 0, 0.54);
  min-width: 800px;
  max-width: 1400px;
  padding: 20px 30px;
  align-items: center;
  height: 100%;
  width: 100%;
}

.search-box {
  display: flex;
  flex-direction: column;
  margin-bottom: 20px;
}

.search-elements,
.search-box > div {
  display: flex;
  justify-content: center;
  align-content: center;
  width: 100%;
}

.search-element {
  margin: 20px;
}

.search-element > span {
  margin-right: 15px;
  font-weight: 700;
  font-size: 18px;
}
.draggable-box {
  position: absolute;
  width: 500px;
  height: 600px;
  z-index: 10;
  background: #00000060;
  border: 2px solid rgba(255, 255, 255, 0.5);
  border-radius: 20px;
  backdrop-filter: blur(15px);
  padding: 0 30px;
  right: 20px;
}

.draggable-icon {
  text-align: right;
  padding-top: 10px;
  text-align: center;
  color: #fff;
  font-size: 40px;
  width: 100%;
  cursor: pointer;
}

.map-box {
  display: flex;
}
</style>
<style>
#map {
  /* width: 500px; */
  height: 500px;
}
#marker-list {
  height: 500px;
  width: 400px;
  overflow: scroll;
  padding: 0 20px;
  border: 1px solid rgba(255, 255, 255, 0.5);
  background: rgba(0, 0, 0, 0.4);
  margin-right: 10px;
}
</style>
