import { defineStore } from 'pinia'

interface Category {
    id: number
    name: string
    image: string
    description: string
}

export const useForumCategoryStore = defineStore('forumCategory', {
    state: () => ({
        categories: [] as Category[],
        currentCategory: 0
    }),

    actions: {
        setCategories(categories: Category[]) {
            this.categories = categories;
        },

        setCurrentCategory(id: number) {
            this.currentCategory = id
        }
    },

    getters: {
        getCategoryById: (state) => {
            return (id: number): Category | null => {
                // 从categories数组中查找id匹配的分类
                const matched = state.categories.find(category => category.id === id);
                return matched ? matched : null; // 找不到返回null
            };
        },
    },
})