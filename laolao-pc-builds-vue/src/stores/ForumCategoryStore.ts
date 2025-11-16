import { defineStore } from 'pinia'

interface Category {
    id: number
    icon: string
    name: string
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
            this.categories.forEach(category => {
                category.icon = this.getIcon(category.id)
            })
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

        getIcon: () => {
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
        },
    },
})