<script setup>
defineProps({ book: Object })
</script>
<template>
  <tr class="text-center">
    <th>{{ book.isbn }}</th>
    <td>
      <router-link
        style="text-decoration: none"
        :to="{ name: 'book-view', query: { isbn: book.isbn } }"
      >
        {{ book.title }}
      </router-link>
    </td>
    <td>{{ book.author }}</td>
    <td>{{ book.price }}</td>
  </tr>
</template>
<style scoped></style>
