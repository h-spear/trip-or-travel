<script setup>
import { ref } from 'vue'
import { uploadImage } from '@/api/image.js'

const image = ref(null)

function getFileName(data) {
  console.log(data)
  const fileReader = new FileReader()
  const formData = new FormData()
  // console.log(data[0])
  formData.append(data[0])
  for (let i of formData) {
    console.log(i)
  }
  console.log(fileReader.readAsDataURL(data[0]))
  fileReader.onload = () => {
    console.log(fileReader.result)
    image.value = fileReader.result
    uploadImage(
      fileReader.result,
      (data) => {
        console.log('success', data)
      },
      (error) => {
        console.log('error ', error)
      }
    )
  }
  // image.value = tmp.readAsDataURL
}
// base64(file) {
//   // 비동기적으로 동작하기 위하여 promise를 return 해준다.
//   return new Promise(resolve => {
//     // 업로드된 파일을 읽기 위한 FileReader() 객체 생성
//     let a = new FileReader()
//     // 읽기 동작이 성공적으로 완료됐을 때 발생
//     a.onload = e => {
//       resolve(e.target.result)
// 			// 썸네일을 보여주고자 하는 <img>에 id값을 가져와 src에 결과값을 넣어준다.
//       const previewImage = document.getElementById('preview')
//       previewImage.src = e.target.result
//     }
// 		// file 데이터를 base64로 인코딩한 문자열. 이 문자열을 브라우저가 인식하여 원래 데이터로 만들어준다.
//     a.readAsDataURL(file)
//   })
// }

// async getFileName(files) {
//   this.fileName = files[0]
//   await this.base64(this.fileName)
// }
</script>

<template>
  <div class="mt-4 p-5 bg-primary text-white rounded">
    <h1>이곳은 테스트페이지입니다</h1>
    <img :src="image" alt="testing" />
    <div>
      <input type="file" id="upload-image" hidden @change="getFileName($event.target.files)" />
      <label for="upload-image">
        <img src="@/assets/ssafy_logo.png" />
      </label>
    </div>
  </div>
</template>

<style scoped></style>
