
This project consists of an application used as a plugin for an ontology editor, and is composed of multiple elements. 
It is a modular software program developed in Java, which uses OpenAi’s GPT 3 davinci language model for developing Functional Syntax axioms that are used to add components to a new or existing ontology.
The presented plugin is created as a tab and displayed as a view component. 
Users connect with the plugin as following: they write a sentence in English and press a button to trigger the system, thus adding elements to the active ontology in real time. 
The component displayed as a panel on the user interface sends the entered prompts to a separate component, which in turn sends them to the trained model and returns the result back to the view component, where the result is transformed into ontology axioms. 
Davinci base GPT model is trained using fine-tuning and a proper data set, so as to generate functional syntax axioms.
