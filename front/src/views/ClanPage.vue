<template>
  <div :class="this.$route.name === 'ClanForm' ? '' : 'clan-page-wrapper'">
    <router-view />
  </div>
</template>

<style scoped>
.clan-page-wrapper {
  padding: 15px;
}
</style>