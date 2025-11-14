import { defineStore } from 'pinia'

interface Category {
    id: number
    name: string
    description: string
}

export const useForumCategoryStore = defineStore('forumCategory', {
    state: () => ({
        category: {
            id: 0,
            name: '',
            description: ''
        } as Category,
        currentCategory: 0
    }),

    actions: {
        setCategory(category: Category) {
            this.category.id = category.id
            this.category.name = category.name
            this.category.description = category.description
        },

        clearCategory() {
            this.category.id = 0
            this.category.name = ''
            this.category.description = ''
        },

        setCurrentCategory(id: number) {
            this.currentCategory = id
        }
    },

    getters: {
        // 添加 getIconClass 作为 getter
        getIconClass: () => {
            return (id: number) => {
                const iconMap: Record<number, string> = {
                    1: 'icon-[noto--old-man-light-skin-tone] text-3xl',
                    2: 'icon-[noto--smiling-face-with-open-mouth-and-cold-sweat] text-3xl',
                    3: 'icon-[noto--nerd-face] text-3xl',
                    4: 'icon-[noto--carpentry-saw] text-3xl',
                    5: 'icon-[noto--hot-face] text-3xl'
                };
                return iconMap[id] || '';
            }
        }
    },
})