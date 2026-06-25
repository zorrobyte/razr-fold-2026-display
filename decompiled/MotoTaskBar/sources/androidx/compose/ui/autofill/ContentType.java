package androidx.compose.ui.autofill;

/* JADX INFO: compiled from: ContentType.android.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ContentType {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ContentType.android.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ContentType Username = ContentType_androidKt.ContentType("username");
        private static final ContentType Password = ContentType_androidKt.ContentType("password");
        private static final ContentType EmailAddress = ContentType_androidKt.ContentType("emailAddress");
        private static final ContentType NewUsername = ContentType_androidKt.ContentType("newUsername");
        private static final ContentType NewPassword = ContentType_androidKt.ContentType("newPassword");
        private static final ContentType PostalAddress = ContentType_androidKt.ContentType("postalAddress");
        private static final ContentType PostalCode = ContentType_androidKt.ContentType("postalCode");
        private static final ContentType CreditCardNumber = ContentType_androidKt.ContentType("creditCardNumber");
        private static final ContentType CreditCardSecurityCode = ContentType_androidKt.ContentType("creditCardSecurityCode");
        private static final ContentType CreditCardExpirationDate = ContentType_androidKt.ContentType("creditCardExpirationDate");
        private static final ContentType CreditCardExpirationMonth = ContentType_androidKt.ContentType("creditCardExpirationMonth");
        private static final ContentType CreditCardExpirationYear = ContentType_androidKt.ContentType("creditCardExpirationYear");
        private static final ContentType CreditCardExpirationDay = ContentType_androidKt.ContentType("creditCardExpirationDay");
        private static final ContentType AddressCountry = ContentType_androidKt.ContentType("addressCountry");
        private static final ContentType AddressRegion = ContentType_androidKt.ContentType("addressRegion");
        private static final ContentType AddressLocality = ContentType_androidKt.ContentType("addressLocality");
        private static final ContentType AddressStreet = ContentType_androidKt.ContentType("streetAddress");
        private static final ContentType AddressAuxiliaryDetails = ContentType_androidKt.ContentType("extendedAddress");
        private static final ContentType PostalCodeExtended = ContentType_androidKt.ContentType("extendedPostalCode");
        private static final ContentType PersonFullName = ContentType_androidKt.ContentType("personName");
        private static final ContentType PersonFirstName = ContentType_androidKt.ContentType("personGivenName");
        private static final ContentType PersonLastName = ContentType_androidKt.ContentType("personFamilyName");
        private static final ContentType PersonMiddleName = ContentType_androidKt.ContentType("personMiddleName");
        private static final ContentType PersonMiddleInitial = ContentType_androidKt.ContentType("personMiddleInitial");
        private static final ContentType PersonNamePrefix = ContentType_androidKt.ContentType("personNamePrefix");
        private static final ContentType PersonNameSuffix = ContentType_androidKt.ContentType("personNameSuffix");
        private static final ContentType PhoneNumber = ContentType_androidKt.ContentType("phoneNumber");
        private static final ContentType PhoneNumberDevice = ContentType_androidKt.ContentType("phoneNumberDevice");
        private static final ContentType PhoneCountryCode = ContentType_androidKt.ContentType("phoneCountryCode");
        private static final ContentType PhoneNumberNational = ContentType_androidKt.ContentType("phoneNational");
        private static final ContentType Gender = ContentType_androidKt.ContentType("gender");
        private static final ContentType BirthDateFull = ContentType_androidKt.ContentType("birthDateFull");
        private static final ContentType BirthDateDay = ContentType_androidKt.ContentType("birthDateDay");
        private static final ContentType BirthDateMonth = ContentType_androidKt.ContentType("birthDateMonth");
        private static final ContentType BirthDateYear = ContentType_androidKt.ContentType("birthDateYear");
        private static final ContentType SmsOtpCode = ContentType_androidKt.ContentType("smsOTPCode");

        private Companion() {
        }

        public final ContentType getPassword() {
            return Password;
        }
    }
}
