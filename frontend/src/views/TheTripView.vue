<script setup>
import { ref, watch, onMounted } from "vue";
import markerInfoWindow from '@/components/map/markerInfoWindow.vue'
import selectedItem from '@/components/map/selectedItem.vue'
import attractionItem from '@/components/map/attractionItem.vue'
var map;
const positions = ref([]);
const markers = ref([]);


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
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);



  } 
});

const initMap = () => {
  const container = document.getElementById("map");
  // 위치는 나중에 사용자 주소 기반으로 설정하자
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);
  
  // 지도 컨트롤러 생성
  map.addControl(new kakao.maps.MapTypeControl(), kakao.maps.ControlPosition.RIGHT);
  map.addControl(new kakao.maps.ZoomControl(), kakao.maps.ControlPosition.RIGHT);


    //testcode
    let testCoord1 = new kakao.maps.LatLng(33.450701, 126.570667)
    let testCoord2 = new kakao.maps.LatLng(33.45, 126.57)
    positions.value = [testCoord1 , testCoord2 ]
    console.log(positions.value)


    		// 지도 클릭 이벤트를 등록한다 (좌클릭 : click, 우클릭 : rightclick, 더블클릭 : dblclick)
		kakao.maps.event.addListener(map, 'click', function (mouseEvent) {
			console.log('지도에서 클릭한 위치의 좌표는 ' + mouseEvent.latLng.toString() + ' 입니다.');
		});	

  loadMarkers();
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


const loadMarkers = () => {
    // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
    deleteMarkers();

    // const imgSrc = require("@/assets/map/markerStar.png");
    // const imgSize = new kakao.maps.Size(24, 35);
    // const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  let tmp = 0;
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      // position: position.latlng, // 마커를 표시할 위치
      position: position, // 마커를 표시할 위치
      title: 'test', // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지

    });
    marker.testvar = tmp++

    // kakao.maps.event.addListener(marker, 'mouseover', function() {
		//     console.log('마커에 mouseover 이벤트가 발생했습니다!', marker);
		// });

		// // 마커에 mouseout 이벤트 등록
		// kakao.maps.event.addListener(marker, 'mouseout', function() {
		//     console.log('마커에 mouseout 이벤트가 발생했습니다!');
		// });

    // var iwContent = `<div style="padding:5px;" @click="clickTest(tmp)">${tmp}</div>` // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다

    var iwContent = document.createElement('div')
    var title = document.createElement('div')
    title.appendChild(document.createTextNode('testing'))
    title.onclick = ()=>{
      console.log(marker.testvar)
    }
    iwContent.appendChild(title)

    // 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({
        content : iwContent,
        removable : true
    });
    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', function() {
          infowindow.open(map, marker);  
    })

    markers.value.push(marker);
  });
}

//   // 4. 지도를 이동시켜주기
//   // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
//   const bounds = positions.value.reduce(
//     (bounds, position) => bounds.extend(position.latlng),
//     new kakao.maps.LatLngBounds()
//   );

//   map.setBounds(bounds);
// };

const deleteMarkers = () => {
  if (markers.value.length > 0) {
    markers.value.forEach((marker) => marker.setMap(null));
  }
};
</script>

<template>
  <div class="container-fluid border border-info">
    <div class="row border border-dark border-2">
      <div id="map" class="col-9"></div>
      <attractionItem class="col-3 bg-white">dd</attractionItem>
    </div>
    <div class="row border border-white">
      <selectedItem class="bg-white" :markers="markers"></selectedItem>
    </div>
  </div>
</template>

<style>
#map{
  /* width: 500px; */
  height: 500px
}
</style>
