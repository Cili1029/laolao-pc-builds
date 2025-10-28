import type PostSimple from '@/components/PostSimple.vue'
import { defineStore } from 'pinia'

// 帖子简单信息
interface PostSimple {
    id: number
    title: string
    viewCount: number
    likeCount: number
    commentCount: number
}

export const usePostStore = defineStore('post', {
    state: () => ({
        postSimple: {
            id: 0,
            title: '',
            viewCount: 0,
            likeCount: 0,
            commentCount: 0,
        } as PostSimple,
        signedIn: false
    }),

    actions: {
        setPostSimple(any: any) {
            const postSimple = any as PostSimple
            this.postSimple.id = postSimple.id
            this.postSimple.title = postSimple.title
            this.postSimple.viewCount = postSimple.viewCount
            this.postSimple.likeCount = postSimple.likeCount
            this.postSimple.commentCount = postSimple.commentCount
        }
    }
})