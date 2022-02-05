import Vue from 'vue';
import Router from 'vue-router';

import About from '../views/About';
import LoginPage from '../views/LoginPage';

import alarmRoutes from './routes-alarms';
import exampleRoutes from './routes-examples';
import systemRoutes from './routes-system';
import userRoutes from './routes-users';
import dataSourceRoutes from './routes-datasources';
import eventRoutes from './routes-events';

import GraphicalView from '../views/GraphicalViews';
import PublicView from '../views/GraphicalViews/AnonymousViewPage';
import Reports from '../views/Reports';
import SynopticPanelMenu from '../views/SynopticPanel/SynopticPanelMenu';
import SynopticPanelItem from '../views/SynopticPanel/SynopticPanelItem';
import WatchList from '../views/WatchList';
import WatchListItem from '../views/WatchList/WatchListItem';
import UnauthorizedPage from '../views/401.vue';

import store from '../store/index';
import GraphicalViewPage from '../views/GraphicalViews/GraphicalViewPage';

Vue.use(Router);

const routing = new Router({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes: [
		
		{
			path: '/login',
			name: 'login',
			component: LoginPage,
		},
		{
			path: '/401',
			name: '401',
			component: UnauthorizedPage,
		},
		{
			path: '/about',
			name: 'about',
			component: About,
			meta: {
				requiresAuth: true
			},
		},
        {
            path: '/graphical-view',
			name: 'graphical-view',
			component: GraphicalView,
			meta: {
                requiresAuth: true
            },
			children: [
				{
					path: ':id',
					component: GraphicalViewPage
				}
			]

        },
		{
            path: '/public-view',
			name: 'public-view',
			component: PublicView,
			children: [
				{
					path: ':id',
					component: GraphicalViewPage
				}
			]

        },
		{
			path: '/synoptic-panel',
			name: 'synoptic-panel',
			component: SynopticPanelMenu,
			children: [
				{
					path: ':id',
					component: SynopticPanelItem,
				},
			],
		},
        {
            path: '/reports',
			name: 'reports',
			component: Reports,
			meta: {
                requiresAuth: true
            }

        },
		{
			path: '/watch-list',
			name: 'watch-list',
			component: WatchList,
			children: [
				{
					path: ':id',
					component: WatchListItem,
				}
			]
		},
        ...alarmRoutes,
        ...dataSourceRoutes,
        ...eventRoutes,
        ...userRoutes,
        ...systemRoutes,
		...exampleRoutes,		

	],
});

routing.beforeEach((to, from, next) => {
	if (to.meta.requiresAuth) {
		if (!store.state.loggedUser) {
			store.dispatch('getUserInfo')
				.catch(() => {
					next({ name: 'login' });
				})
		}
	}
	if(to.meta.requiresAdmin) {
		if(!!store.state.loggedUser && !store.state.loggedUser.admin) {
			next({ name: '401' });
		}
	}
	next();
})

export default routing;