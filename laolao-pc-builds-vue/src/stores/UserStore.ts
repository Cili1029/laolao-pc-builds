import { defineStore } from 'pinia'

interface User {
    id: number
    avatar: string
    username: string
    name: string
}

export const useUserStore = defineStore('user', {
    state: () => ({
        user: {
            id: 0,
            avatar: '',
            username: '',
            name: ''
        } as User,
        signedIn: false
    }),

    actions: {
        setUser(category: User) {
            this.user.id = category.id
            this.user.avatar = category.avatar
            this.user.username = category.username
            this.user.name = category.name
            this.signedIn = true
        },

        clearUser() {
            this.user.id = 0
            this.user.avatar = ''
            this.user.username = ''
            this.user.name = ''
            this.signedIn = false
        }
    }
})