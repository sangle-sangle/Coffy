<template>
  <div class="ctr-90">
    <div v-for="category in categorys" :key="category.id">
        {{category.title}}
        {{category.solved }} / {{ category.problems.length}}
        <div v-for="problem in category.problems" :key="problem.id">
            <router-link :to="`/code/game/${category.title}/${problem.id}`" > {{problem.id}} 번문제 풀기 </router-link>
        </div>
    </div>
  </div>
</template>

<script>
export default {
    mounted(){
        
    },
    data() {
        return {
            categorys : [{
                id : 1,
                title : 'flex',
                solved : 0,
                problems : [
                    {id : 1},{id:2},{id : 3},{id : 4},{id: 5}
                ]
            },
            {
                id : 2,
                title : 'text',
                solved : 0,
                problems : [
                    {id : 1}, {id : 2}
                ]
            }
            ],
        }
    }
}
</script>

<style>
.ctr-90{
    margin:0 auto;
    width : 90%;
}
</style>