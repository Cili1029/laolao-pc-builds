import { defineStore } from 'pinia'

interface User {
    id: number
    avatar: string
    username: string
    name: string
    admin: number
}

export const useUserStore = defineStore('user', {
    state: () => ({
        user: {
            id: 0,
            avatar: '',
            username: '',
            name: '',
            admin: 0
        } as User,
        signedIn: false
    }),

    actions: {
        setUser(user: User) {
            this.user.id = user.id
            this.user.avatar = user.avatar
            this.user.username = user.username
            this.user.name = user.name
            this.user.admin = user.admin
            this.signedIn = true
        },

        clearUser() {
            this.user.id = 0
            this.user.avatar = ''
            this.user.username = ''
            this.user.name = ''
            this.user.admin = 0
            this.signedIn = false
        }
    }
})