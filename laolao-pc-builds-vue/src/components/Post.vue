<template>
    <div>
        aaaaaaaaaa
    </div>
</template>

<script setup lang="ts">
    import axios from '@/utils/myAxios'
    import { onMounted } from 'vue'
    import { useRoute } from 'vue-router'
    const route = useRoute()
    const id = Number(route.params.id)

    onMounted(() => {
        getPost(id)
    })

    const getPost = async (id: number) => {
        const response = await axios.get("/user/forum/post", {
            params: {
                id: id
            }
        })
    }
</script>

<style scoped></style>