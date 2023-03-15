import React,{ useState } from 'react';
import {
    SafeAreaView,
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    TextInput,
    useColorScheme,
    View,
    Button, TouchableOpacity,
} from 'react-native';

import {
    Colors,
    DebugInstructions,
    Header,
    LearnMoreLinks,
    ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

function EmailInput(props){

    const [text, onChangeText] = useState('');

    return(
        <View style={styles.container}>
            <View style={{flex: 2}}/>
            <Text style={styles.textMain}>Email을 입력해주세요.</Text>
            <TextInput style = {styles.input}
                       placeholder="Type here..."
                       onChangeText={onChangeText}
                       value={text}/>
            <View style={{flex: 2}}/>
            <TouchableOpacity style={styles.buttonLogin} onPress={()=>{
                props.navigation.navigate('EmailInput')
            }}>
                <Text style={styles.textLogin}>Login</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        alignItems: 'center',
        backgroundColor: "white",
    },
    buttonLogin: {
        backgroundColor: "#E87D30",
        width: "100%",
        height: "10%",
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
    line: {
        flex: 1,
        height: 1,
        backgroundColor: '#E87D30',
        width: "80%",
    },
    textLogin: {
        textAlign: 'center',
        color: "white",
        fontWeight: "bold",
        fontSize: 25,
    },
    input: {
        width: "80%",
        borderBottomWidth: 1,
        borderBottomColor: '#E87D30',
    },
});

export {EmailInput}

