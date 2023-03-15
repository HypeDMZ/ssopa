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

function SignUp(props){
    return(
        <View>
            <Text> ㅎㅎ </Text>
            <Button title="Go to User Screen"
                    onPress={()=>{
                        props.navigation.navigate('Login')
                    }}/>
        </View>

    )
}

export {SignUp}