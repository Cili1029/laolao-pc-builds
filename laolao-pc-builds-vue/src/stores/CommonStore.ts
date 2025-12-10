import { defineStore } from 'pinia'

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
    }
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