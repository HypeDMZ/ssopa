import React from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    useColorScheme,
    View,
    Button,
} from 'react-native';

import {
    Colors,
    DebugInstructions,
    Header,
    LearnMoreLinks,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

function Login(props){
    return(
        <View>
            <Text> hi </Text>
            <Button title="Go to User Screen"
                    onPress={()=>{
                        props.navigation.navigate('SignUp')
                    }}/>
        </View>
    )
}

export {Login}