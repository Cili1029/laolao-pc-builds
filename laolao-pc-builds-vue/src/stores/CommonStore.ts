import { defineStore } from 'pinia'

interface UserSimple {
  id: number
  username: string
  avatar: string
}

interface OnlineUser {
  onlineCount: number
  onlineUsers: UserSimple[]
}

export const useCommonStore = defineStore('common', {
  state: () => ({
    isShowFunction: true,
    search: {
      searchContent: '',
      search: false,
    },
    sidebar: {
      user: false,
      shop: false,
      forum: false
    },
    onlineUser: {
      onlineCount: 0, // 初始值为 0（数字类型）
      onlineUsers: [] // 初始值为空数组（UserSimple 数组类型）
    } as OnlineUser
  }),

  actions: {
    setShowFunction(status: boolean) {
      this.isShowFunction = status;
    },
    setSearchContent(content: string) {
      this.search.searchContent = content
    },
  },
})