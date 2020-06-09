<template>
  <div class="pagination-wrapper">
    <div id="slot-pagination">
      <div class="prev-btn" @click="setNowPage(nowPage - 1)"><i class="fas fa-angle-left"></i></div>
      <div v-for="idx in this.pageIndexes"
        :key="idx"
        class="page-btn"
        :style="{ backgroundColor: idx === nowPage ? '#aaa' : '#555', color: idx === nowPage ? '#333' : '#eee' }"
        @click="setNowPage(idx)"
      >
        {{ idx }}
      </div>
      <div class="next-btn" @click="setNowPage(nowPage + 1)"><i class="fas fa-angle-right"></i></div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: {
    itemCount: {
      type: Number,
      default: 1
    },
    splitCount: {
      type: Number,
      default: 12
    }
  },
  data() {
    return {
      nowPage: 1,
      allPage: 1,
      pageIndexes: []
    }
  },
  created() {
    this.calcPage();
  },
  methods: {
    calcPage() {
      this.allPage = parseInt((this.itemCount - 1) / this.splitCount) + 1;
      let maxLimitIdx = this.allPage >= 4 ? 4 : this.allPage;
      for (let i = 1; i <= maxLimitIdx; ++i) {
        this.pageIndexes.push(i)
      }
    },
    setNowPage(pageNm) {
      if (pageNm >= 1 && pageNm <= this.allPage) {
        this.nowPage = pageNm;
        this.$emit('setNowPage', this.nowPage);
      }
    }
  },
  watch: {
    nowPage: {
      handler(newValue, oldValue) {
        if (parseInt((newValue - 1) / 4) !== parseInt((oldValue - 1) / 4)) {
          this.pageIndexes = []
          if (newValue - oldValue === 1) {
            let maxLimitIdx = newValue + 3 > this.allPage ? this.allPage : newValue + 3
            for (let i = newValue; i <= maxLimitIdx; ++i) {
              this.pageIndexes.push(i)
            }
          } else {
            for (let i = newValue; i >= newValue - 3; --i) {
              this.pageIndexes.unshift(i)
            }
          }
        }
      }
    }
  }
}
</script>

<style scoped>
#slot-pagination {
  display: inline-flex;
  justify-content: center;
  align-items: center;
  width: 100%;
}

div[class$='-btn'] {
  font-family: 'Gothic A1';
  width: 30px;
  height: 30px;
  color: #eee;
  text-align: center;
  padding: 6px;
  margin: 0 4px;
  background-color: #555;
  border: transparent;
  border-radius: 50%;
  box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}

div[class$='-btn']:hover {
  cursor: pointer;
}
</style>