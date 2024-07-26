package com.example.InformationalPortal.testConnect.services.bank;

import com.example.InformationalPortal.testConnect.configs.SimpleEncryption;
import com.example.InformationalPortal.testConnect.models.bank.BankBalance;
import com.example.InformationalPortal.testConnect.models.bank.BankCard;
import com.example.InformationalPortal.testConnect.models.bank.UserBank;
import com.example.InformationalPortal.testConnect.repositories.bank.BankBalanceRepository;
import com.example.InformationalPortal.testConnect.repositories.bank.BankCardRepository;
import com.example.InformationalPortal.testConnect.repositories.bank.UserBankRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class BankService {

    private final BankCardRepository bankCardRepository;
    private final UserBankRepository userBankRepository;
    private final BankBalanceRepository bankBalanceRepository;

    private BankCard card;
    private UserBank user;
    private BankBalance balance;


    public BankCard getCard() {
        return card;
    }

    public UserBank getUser() {
        return user;
    }

    public BankBalance getBalance() {
        return balance;
    }

    public BankService(BankCardRepository bankCardRepository, UserBankRepository userBankRepository, BankBalanceRepository bankBalanceRepository) {
        this.bankCardRepository = bankCardRepository;
        this.userBankRepository = userBankRepository;
        this.bankBalanceRepository = bankBalanceRepository;
    }

    public boolean registrOk(String login, String password, String tel, String nameBank, String numberCard, String nameSystem, String NameHold, String dateCard, String cvv) {

        try {
            String pass = SimpleEncryption.encrypt(password);

            BankCard bankCard = new BankCard(0, nameBank, numberCard, nameSystem, NameHold, dateCard, cvv);
            BankBalance bankBalance = new BankBalance(0, numberCard, "0");
            UserBank userBank = new UserBank(0, login, pass, tel, numberCard);
            bankCardRepository.save(bankCard);
            userBankRepository.save(userBank);
            bankBalanceRepository.save(bankBalance);

            bankCard = null;
            userBank = null;
            bankBalance = null;

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean loginOk(String login, String password, String cvv) {

        try {
            List<BankCard> cards = (List<BankCard>) bankCardRepository.findByCvv(cvv);
            UserBank userBank = userBankRepository.findByLogin(login).get();
            BankCard bankCard = null;

            for (BankCard card : cards) {
                if (card.getCardNumber().equals(userBank.getCardNumber())) {
                    bankCard = card;
                }
            }
            BankBalance bankBalance = bankBalanceRepository.findByCardNumber(bankCard.getCardNumber()).get();
            String pass = SimpleEncryption.decrypt(userBank.getPassword());
            System.out.println(pass);
            if (userBank != null && bankCard != null && pass.equals(password) && userBank.getCardNumber().equals(bankCard.getCardNumber())) {
                this.card = bankCard;
                this.user = userBank;
                this.balance = bankBalance;

            } else {
                return false;
            }

            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public void getInfo(Model model) {
        BankBalance b = bankBalanceRepository.findByCardNumber(user.getCardNumber()).get();
        model.addAttribute("login1", user.getLogin());
        model.addAttribute("login2", user.getLogin());
        model.addAttribute("tel", user.getTel());
        model.addAttribute("nameBank", card.getBankName());
        model.addAttribute("numberCard", card.getCardNumber());
        model.addAttribute("nameSystem", card.getPaymentSystem());
        model.addAttribute("dateCard", card.getExpiryDate());
        model.addAttribute("balance", b.getCardBalance());
        b = null;
    }


    public boolean topUp(String amount) {

        try {
            BankBalance bankBalance = bankBalanceRepository.findByCardNumber(card.getCardNumber()).get();
            Integer balance = Integer.valueOf(bankBalance.getCardBalance());
            Integer topUp = Integer.valueOf(amount);
            Integer sum = balance + topUp;
            bankBalanceRepository.updateBankBalanceByCardNumber(card.getCardNumber(), String.valueOf(sum));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean debit(String amount) {

        try {
            BankBalance bankBalance = bankBalanceRepository.findByCardNumber(card.getCardNumber()).get();
            Double balance = Double.valueOf(bankBalance.getCardBalance());
            Double topUp = Double.valueOf(amount);
            Double desc = balance - topUp;
            System.out.println(desc.toString());
            bankBalanceRepository.updateBankBalanceByCardNumber(card.getCardNumber(), String.valueOf(desc));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean cardEmpty(){

        try {
            bankBalanceRepository.updateBankBalanceByCardNumber(card.getCardNumber(), String.valueOf(0));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }


}
