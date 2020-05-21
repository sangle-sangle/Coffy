<template>
  <div>
    Code Main Page
  </div>
</template>

<script>
import { fetchMyInfo } from '@/api/user.js'

export default {
  data() {
    return {
    }
  },
  mounted(){
    this.userdata()
  },
  methods : {
    async userdata() {
      const data = await fetchMyInfo()
      console.log(data)
    }
  }
}
</script>

<style>

</style>