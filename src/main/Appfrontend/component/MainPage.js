import React from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    TouchableOpacity,
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

function MainPage(props){
    return(
        <View style={styles.container}>
            <View style={{flex: 2}}></View>
            <Image source={require('../img/logo.png')} style={styles.logo}/>
            <Text style={styles.textMain}>SSOPA와 함께하는 학교 생활</Text>
            <View style={{flex: 2}}></View>
            <View style={{flexDirection: 'row', flex: 2}}>
                <TouchableOpacity style={styles.buttonLogin} onPress={()=>{
                    props.navigation.navigate('Login')
                }}>
                    <Text style={styles.textLogin}>Login</Text>
                </TouchableOpacity>

                <TouchableOpacity style={styles.buttonSignUp} onPress={()=>{
                    props.navigation.navigate('SignUp')}}>
                    <Text style={styles.textSignUp}>SignUp</Text>
                </TouchableOpacity>
            </View>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
    },
    buttonLogin: {
        backgroundColor: "#E87D30",
        borderRadius: 6,
        width: "40%",
        height: "35%",
        marginLeft: 10,
        justifyContent: 'center',
    },
    buttonSignUp: {
        backgroundColor: "white",
        borderRadius: 6,
        width: "40%",
        height: "35%",
        marginLeft: 10,
        borderStyle: "solid",
        borderWidth: 0.8,
        borderColor: "#888",
        justifyContent: 'center',
    },
    logo: {
        width: 200,
        height: 140,
        marginBottom: 20,
    },
    textMain: {
        textAlign: 'center',
        color: "#E87D30",
        fontWeight: "bold",
        fontSize: 25,
    },
    textLogin: {
        textAlign: 'center',
        color: "white",
        fontWeight: "bold",
        fontSize: 25,
    },
    textSignUp: {
        textAlign: 'center',
        fontWeight: "bold",
        color: "#444",
        fontSize: 25,
    }
});

export {MainPage}