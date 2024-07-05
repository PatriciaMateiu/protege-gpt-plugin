package utcn.iva.gpt3.protege;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import org.protege.editor.owl.ui.action.SelectedOWLClassAction;
import org.semanticweb.owlapi.model.OWLClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateOntologyAction extends SelectedOWLClassAction {

    @Override
    protected void initialiseAction() throws Exception {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        OWLClass selectedClass = getOWLWorkspace().getOWLSelectionModel().getLastSelectedClass();

        if (selectedClass != null){
            openDialog();
        }
    }

    private void openDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Create a new ontology using GPT-3");
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Write the text you want to add to the ontology"));
        JTextArea textArea = new JTextArea(20, 50);
        JTextArea finalOntology = new JTextArea(10, 20);
        finalOntology.setBackground(Color.LIGHT_GRAY.brighter());
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel.add(areaScrollPane);
        JButton jButton = new JButton("ADD");
        jButton.setSize(15, 15);
        jPanel.add(jButton);
        String token = "";
        jPanel.add(finalOntology);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String initial = finalOntology.getText();
                finalOntology.setText(initial + '\n' + e.toString());
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
                String dialogPrompt = "Write '" + textArea.getText() + "' in OWL Functional Syntax. Example: '" + example + "'";
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
                    String existingText = finalOntology.getText();
                    String text = choice.getMessage().getContent();
                    finalOntology.setText(existingText + "\n\n" + text);
                });

                service.shutdownExecutor();
            }
        });
        dialog.add(jPanel);
        dialog.pack();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setSize(new Dimension(800, 800));
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
