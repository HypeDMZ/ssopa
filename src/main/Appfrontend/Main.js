import React from 'react';
import {PropsWithChildren} from 'react'
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    useColorScheme,
    View,
} from 'react-native';

import {
    Colors,
    DebugInstructions,
    Header,
    LearnMoreLinks,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';
import {Login} from './component/Login'
import {SignUp} from './component/SignUp'
import {MainPage} from './component/MainPage'

const Stack = createStackNavigator();
function Main(){

    return (
        <NavigationContainer>
            <Stack.Navigator>
                <Stack.Screen name={'MainPage'} component={MainPage}/>
                <Stack.Screen name={'Login'} component={Login}/>
                <Stack.Screen name={'SignUp'} component={SignUp}/>
            </Stack.Navigator>
        </NavigationContainer>
    );
}

const styles = StyleSheet.create({
    sectionContainer: {
        marginTop: 32,
        paddingHorizontal: 24,
    },
    sectionTitle: {
        fontSize: 24,
        fontWeight: '600',
    },
    sectionDescription: {
        marginTop: 8,
        fontSize: 18,
        fontWeight: '400',
    },
    highlight: {
        fontWeight: '700',
    },
});

export default Main;