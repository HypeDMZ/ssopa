import React from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    useColorScheme,
    Image,
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

import MainLog from '../img/logo.png';

const styles = StyleSheet.create({
    button: {
        background: "#E87D30",
        borderRadius: "40",
    },
    logo: {
        width: "60",
        height: "60",
    }
});
function MainPage(props){

    return(
        <View>
            <Text> SSopa </Text>
            <Image source = {require('../img/logo.png')}
                   style={styles.logo}/>
            <Button title="Login"
                    style = {styles.button}
                    onPress={()=>{
                        props.navigation.navigate('Login')
                    }}/>
            <Button title="SignUp"
                    style = {styles.button}
                    onPress={()=>{
                        props.navigation.navigate('SignUp')
                    }}/>
        </View>
    )
}

export {MainPage}