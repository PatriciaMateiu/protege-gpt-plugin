package utcn.iva.gpt3.protege;

import org.protege.editor.owl.ui.clsdescriptioneditor.ExpressionEditor;
import org.protege.editor.owl.ui.view.AbstractOWLViewComponent;

import javax.swing.*;
import java.awt.*;


public class ProtegeView extends AbstractOWLViewComponent {

    // private static final Logger log = LoggerFactory.getLogger(SREProtegeView.class);


    //private static final Marker marker = MarkerFactory.getMarker("Create ontologies with GPT-3");

    //private static final Logger logger = LoggerFactory.getLogger(ProtegeView.class);

    private final JButton addButton = new JButton("ADD");
    private String token = "";

    //private JTextArea textArea = new JTextArea("");
    private ExpressionEditor<String> owlDescriptionEditor;


    @Override
    protected void initialiseOWLView() throws Exception {

        setLayout(new BorderLayout(10, 10));
        JComponent promptPanel = createPromptPanel();
        //JComponent resultsPanel = createResultsPanel();

        //JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT, promptPanel, resultsPanel);
        //splitter.setDividerLocation(0.3);

        add(promptPanel, BorderLayout.CENTER);
    }


    private JComponent createPromptPanel() {
        JPanel promptPanel = new JPanel(new BorderLayout());

        //final OWLExpressionChecker<OWLClassExpression> checker = getOWLModelManager().getOWLExpressionCheckerFactory().ge;
        //final OWLExpressionChecker<String> checker = getOWLModelManager().getOWLExpressionCheckerFactory().
       /* owlDescriptionEditor = new ExpressionEditor<String>();

        owlDescriptionEditor.addStatusChangedListener(newState -> {
            addButton.setEnabled(newState);
        });*/

        //promptPanel.add(ComponentFactory.createScrollPane(owlDescriptionEditor), BorderLayout.CENTER);
        //promptPanel.add(ComponentFactory.createScrollPane(textArea), BorderLayout.CENTER);
        JPanel buttonHolder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton.addActionListener(e -> addToOntology());
        buttonHolder.add(addButton);

        promptPanel.add(buttonHolder, BorderLayout.SOUTH);
        promptPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEmptyBorder(),
                        "Add a text that will be used to enrich the ontology"),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));

        return promptPanel;
    }

    /*private JComponent createResultsPanel() {
        JComponent resultsPanel = new JPanel(new BorderLayout(10, 10));
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Results"),
                BorderFactory.createEmptyBorder(3, 3, 3, 3)));
        JTextArea textArea1 = new JTextArea("HELLO");
        resultsPanel.add(ComponentFactory.createScrollPane(textArea1));

        return resultsPanel;
    }*/
    @Override
    protected void disposeOWLView() {

    }

    private void addToOntology() {
        /*   *//* String initial = finalOntology.getText();*//*
         *//*     finalOntology.setText(initial + '\n' + e.toString());*//*
        OpenAiService service = new OpenAiService(token);
        List<ChatMessage> messages = new ArrayList<ChatMessage>();
        String example = "Declaration(NamedIndividual(:Anna))\n" +
                "Declaration(NamedIndividual(:Mila))\n" +
                "Declaration(Class(:Girl))\n" +
                "Declaration(Class(:Boy))\n" +
                "Declaration(NamedIndividual(:Frank))\n" +
                "Declaration(NamedIndividual(:Joe))\n" +
                "Declaration(NamedIndividual(:Hanz))\n" +
                "ClassAssertion(:Girl :Anna)\n" +
                "ClassAssertion(:Girl :Mila)\n" +
                "ClassAssertion(:Boy :Frank)\n" +
                "ClassAssertion(:Boy :Joe)\n" +
                "ClassAssertion(:Boy :Hanz)";
        String dialogPrompt = "Write '" + *//*textArea.getText() + *//*"' in OWL Functional Syntax. Example: '" + example + "'";
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), dialogPrompt);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(200)
                .logitBias(new HashMap<>())
                .build();
        service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice ->
        {
          *//*  String existingText = finalOntology.getText();
            String text = choice.getMessage().getContent();
            finalOntology.setText(existingText + "\n\n" + text);*//*
        });

        service.shutdownExecutor();
    }*/
    }
}
