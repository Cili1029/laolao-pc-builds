import { defineStore } from 'pinia'

// 帖子简单信息
interface PostSimple {
    id: number
    categoryId: number
    title: string
    likeCount: number
    commentCount: number
    commentedBy: number
    commentedAt: string
}

export const usePostStore = defineStore('post', {
    state: () => ({
        postSimple: {
            id: 0,
            title: '',
            likeCount: 0,
            commentCount: 0,
            commentedBy: 0,
            commentedAt: ''
        } as PostSimple,
        signedIn: false
    }),

    actions: {
        setPostSimple(any: any) {
            const postSimple = any as PostSimple
            this.postSimple.id = postSimple.id
            this.postSimple.title = postSimple.title
            this.postSimple.likeCount = postSimple.likeCount
            this.postSimple.commentCount = postSimple.commentCount
            this.postSimple.commentedAt = postSimple.commentedAt
        }
    }
})