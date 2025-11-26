import { defineStore } from 'pinia'

export const useCommonStore = defineStore('common', {
  state: () => ({
    isShowFunction: true,
  }),

  actions: {
    setShowFunction(status: boolean) { 
      this.isShowFunction = status;
    },
  },
})