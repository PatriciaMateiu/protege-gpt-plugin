package utcn.iva.gpt3.protege;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import com.theokanning.openai.finetune.FineTuneResult;
import com.theokanning.openai.service.OpenAiService;
import io.reactivex.Single;
import org.protege.editor.core.ui.preferences.PreferencesLayoutPanel;
import org.protege.editor.core.ui.preferences.PreferencesPanel;

import org.protege.editor.core.ui.util.ComponentFactory;
import org.protege.editor.owl.ui.action.SelectedOWLClassAction;
import org.semanticweb.owlapi.model.OWLClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;


public class MainPanel extends PreferencesPanel {

    @Override
    public void initialise() throws Exception {
        setLayout(new BorderLayout());

        PreferencesLayoutPanel panel = new PreferencesLayoutPanel();
        add(panel, BorderLayout.NORTH);

        panel.addGroup("REST Service");
        panel.addGroupComponent(new JLabel("GPT-3"));
    }

    @Override
    public void dispose() throws Exception {

    }

    @Override
    public void applyChanges() {

    }

    public static void main(String[] args) throws Exception {
       /* JFrame frame = new JFrame();
        MainPanel panel = new MainPanel();
        panel.initialise();
        frame.add(panel);
        frame.setPreferredSize(new Dimension(600, 300));
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);*/

        JDialog dialog = new JDialog();
        dialog.setTitle("Create a new ontology using GPT-3");
        JPanel jPanel = new JPanel();
        jPanel.add(new JLabel("Write the text you want to add to the ontology"));
        JTextArea textArea = new JTextArea(20, 50);
        JTextArea finalOntology = new JTextArea(30, 50);
        finalOntology.setBackground(Color.LIGHT_GRAY.brighter());
        JScrollPane areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel.add(areaScrollPane);
        JButton jButton = new JButton("ADD");
        jButton.setSize(15, 15);
        jPanel.add(jButton);
        String token = "";
        OpenAiService service = new OpenAiService(token);
        List<FineTuneResult> fineTunes = service.listFineTunes();
        fineTunes.forEach(fineTune -> {
            /*if (fineTune !=null &&  fineTune.getFineTunedModel() != null && fineTune.getFineTunedModel().contains("davinci:ft-personal:ontology-development")) {
                System.out.println(fineTune.toString());
            }*/
            System.out.println(fineTune.toString());
        });

       /* fineTunes.forEach(fineTune -> {
                System.out.println(fineTune.toString());
        });*/
        System.out.println(fineTunes.get(3).toString());
        jPanel.add(ComponentFactory.createScrollPane(finalOntology), BorderLayout.CENTER);
        jButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                List<ChatMessage> messages = new ArrayList<ChatMessage>();
                FineTuneResult fineTuneResult = fineTunes.get(3);
                String example =
                        "Declaration(Class(:Boy))\n" +
                        "Declaration(Class(:Girl))\n" +
                        "Declaration(Class(:Person))\n" +
                        "Declaration(ObjectProperty(:marriedTo))\n" +
                        "Declaration(DataProperty(:isAngry))\n" +
                        "Declaration(NamedIndividual(:Anna))\n" +
                        "Declaration(NamedIndividual(:Frank))\n" +
                        "Declaration(NamedIndividual(:Hanz))\n" +
                        "Declaration(NamedIndividual(:Joe))\n" +
                        "Declaration(NamedIndividual(:Mila))\n" +
                        "SymmetricObjectProperty(:marriedTo)\n" +
                        "SubClassOf(:Boy :Person)\n" +
                        "DisjointClasses(:Boy :Girl)\n" +
                        "SubClassOf(:Girl :Person)\n" +
                        "ClassAssertion(:Girl :Anna)\n" +
                        "ClassAssertion(:Boy :Frank)\n" +
                        "ObjectPropertyAssertion(:marriedTo :Frank :Anna)\n" +
                        "ObjectPropertyAssertion(:marriedTo :Anna :Frank)\n" +
                        "ClassAssertion(:Boy :Hanz)\n" +
                        "ClassAssertion(:Boy :Joe)\n" +
                        "ClassAssertion(:Girl :Mila)\n" +
                        "DataPropertyAssertion(:isAngry :Mila \"true\"^^xsd:boolean)";
                /*String dialogPrompt = "Write '" + textArea.getText() + "' in OWL Functional Syntax. Example: '" + example + "'";*/
                ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), example + "->");
                messages.add(systemMessage);
                CompletionResult completionResult = new CompletionResult();
                Single<CompletionResult> single = Single.just(completionResult);
                CompletionResult actual = service.execute(single);
                List<String> stop = new ArrayList<>();
                stop.add("\n###");

                CompletionRequest completionRequest = CompletionRequest.builder()
                        .n(1)
                        .prompt(textArea.getText())
                        .maxTokens(100)
                        .stop(stop)
                        .logitBias(new HashMap<>())
                        .build();

                completionRequest.setModel(fineTuneResult.getFineTunedModel());
                service.createCompletion(completionRequest).getChoices().forEach(choice ->
                {
                    String existingText = finalOntology.getText();
                    String text = choice.getText();
                    finalOntology.setText(existingText + "\n\n" + text);
                });
               /* ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                        .builder()
                        .messages(messages)
                        .model("gpt-3.5-turbo")
                        .n(1)
                        .maxTokens(1000)
                        .logitBias(new HashMap<>())
                        .build();
                //chatCompletionRequest.setModel(fineTuneResult.getModel());
                service.createChatCompletion(chatCompletionRequest).getChoices().forEach(choice ->
                {
                    String existingText = finalOntology.getText();
                    String text = choice.getMessage().getContent();
                    finalOntology.setText(existingText + "\n\n" + text);
                });*/

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
