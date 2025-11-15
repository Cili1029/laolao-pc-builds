import { defineStore } from 'pinia'

export const useProductStore = defineStore('post', {
    state: () => ({
        id: 0,
        productType: 0
    }),

    actions: {
        setProduct(id: number, productType: number) {
            this.id = id
            this.productType = productType
        }
    }
})